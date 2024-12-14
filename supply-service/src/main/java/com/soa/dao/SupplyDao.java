package com.soa.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.soa.entity.*;
import com.soa.entity.shoe.Heels;
import com.soa.entity.shoe.Shoe;
import com.soa.entity.shoe.Slippers;
import com.soa.entity.shoe.Sneakers;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Stanislav Hlova
 */
@Repository
public class SupplyDao {
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> supplyCollection;

    public SupplyDao(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
        this.supplyCollection = mongoDatabase.getCollection("supply");
    }

    public List<Supply> findAll() {
        FindIterable<Document> documents = supplyCollection.find();
        MongoCursor<Document> cursor = documents.cursor();
        List<Supply> supplyList = new ArrayList<>();
        while (cursor.hasNext()) {
            supplyList.add(mapToSupply(cursor.next()));
        }
        return supplyList;
    }

    private Supply mapToSupply(Document document) {
        List<ShoePairInfoSupply> shoePairInfoSupplyList = new ArrayList<>();
        List<Document> documentList = document.getList("shoe_pair_info_supply", Document.class);
        documentList.forEach(document1 -> {
            shoePairInfoSupplyList.add(mapToShoePairInfoSupply(document1));
        });
        return Supply.builder()
                .id(document.getObjectId("_id"))
                .supplyStatus(SupplyStatus.valueFrom(document.getString("supply_status")))
                .deliveryName(document.getString("delivery_name"))
                .deliveryInvoiceNumber(document.getString("delivery_invoice_number"))
                .deliveryTotal(document.get("delivery_total", Decimal128.class).bigDecimalValue())
                .receivedAt(getLocalDateTime(document, "received_at"))
                .shoePairInfoSupplyList(shoePairInfoSupplyList)
                .build();
    }

    private ShoePairInfoSupply mapToShoePairInfoSupply(Document document) {
        List<ShoePairInfo> shoePairInfoList = new ArrayList<>();
        List<Document> documentList = document.getList("shoe_pair_info", Document.class);
        documentList.forEach(document1 -> {
            shoePairInfoList.add(mapToShoePairInfo(document1));
        });
        return ShoePairInfoSupply.builder()
                .id(document.getObjectId("_id"))
                .siteUrl(document.getString("site_url"))
                .siteName(document.getString("site_name"))
                .shoe(mapToShoe(document.get("shoe", Document.class)))
                .shoePairInfoList(shoePairInfoList)
                .build();
    }

    private ShoePairInfo mapToShoePairInfo(Document document) {
        return ShoePairInfo.builder()
                .id(document.getObjectId("_id"))
                .originalShoePairInfoId(document.getObjectId("original_shoe_pair_info_id"))
                .color(document.getString("color"))
                .size(document.getDouble("size"))
                .article(document.getString("article"))
                .count(document.getInteger("count"))
                .codeList(document.getList("code", String.class))
                .price(document.get("price", Decimal128.class).bigDecimalValue())
                .build();
    }

    private Shoe mapToShoe(Document document) {
        ShoeType shoeType = ShoeType.valueFrom(document.getString("shoe_type"));
        return switch (shoeType) {
            case SNEAKERS -> mapToSneakers(document);
            case HEELS -> mapToHeels(document);
            case SLIPPERS -> mapToSlippers(document);
            default -> shoeBuilder(document).build();
        };
    }

    private Shoe mapToHeels(Document document) {
        return shoeBuilder(document, Heels.builder())
                .insideMaterial(document.getString("inside_material"))
                .soleMaterial(document.getString("sole_material"))
                .insoleMaterial(document.getString("insole_material"))
                .topMaterial(document.getString("top_material"))
                .build();
    }

    private Shoe mapToSlippers(Document document) {
        return shoeBuilder(document, Slippers.builder())
                .soleMaterial(document.getString("sole_material"))
                .insoleMaterial(document.getString("insole_material"))
                .material(document.getString("material"))
                .build();
    }

    private Sneakers mapToSneakers(Document document) {
        return shoeBuilder(document, Sneakers.builder())
                .lacingType(document.getString("lacing_type"))
                .insideMaterial(document.getString("inside_material"))
                .soleMaterial(document.getString("sole_material"))
                .insoleMaterial(document.getString("insole_material"))
                .topMaterial(document.getString("top_material"))
                .build();
    }

    private Shoe.ShoeBuilder shoeBuilder(Document document) {
        return shoeBuilder(document, Shoe.builder());
    }

    private <T extends Shoe.ShoeBuilder> T shoeBuilder(Document document, T shoeBuilder) {
        return (T) shoeBuilder
                .id(document.getObjectId("_id"))
                .originalShoeId(document.getObjectId("original_shoe_id"))
                .name(document.getString("name"))
                .model(document.getString("model"))
                .description(document.getString("description"))
                .createdAt(getLocalDateTime(document, "created_at"))
                .manufacturerName(document.getString("manufacturer_name"))
                .shoeType(ShoeType.valueFrom(document.getString("shoe_type")));
    }

    private static LocalDateTime getLocalDateTime(Document document, String fieldName) {
        return LocalDateTime.ofInstant(
                document.getDate(fieldName).toInstant(),
                ZoneId.systemDefault());
    }

    public Optional<Supply> findById(ObjectId id) {
        Bson filter = Filters.eq("_id", id);
        Document document = supplyCollection.find(filter).first();
        if (document == null) {
            return Optional.empty();
        }
        return Optional.of(mapToSupply(document));
    }

    public Optional<Supply> findSupplyByCode(String shoePairInfoCode) {
        Bson filter = Filters.elemMatch(
                "shoe_pair_info_supply.shoe_pair_info",
                Filters.eq("code", shoePairInfoCode)
        );
        Document document = supplyCollection.find(filter).first();
        if (document == null) {
            return Optional.empty();
        }
        return Optional.of(mapToSupply(document));
    }

    public Supply save(Supply supply) {
        Document document = new Document()
                .append("_id", supply.getId() != null ? supply.getId() : new ObjectId())
                .append("supply_status", supply.getSupplyStatus().value)
                .append("delivery_name", supply.getDeliveryName())
                .append("delivery_invoice_number", supply.getDeliveryInvoiceNumber())
                .append("delivery_total", new Decimal128(supply.getDeliveryTotal()))
                .append("received_at", supply.getReceivedAt() != null ?
                        supply.getReceivedAt().atZone(ZoneId.systemDefault()).toInstant() : null)
                .append("shoe_pair_info_supply", mapShoePairInfoSupplyList(supply.getShoePairInfoSupplyList()));

        supplyCollection.insertOne(document);

        return findById(document.getObjectId("_id")).get();
    }

    public Supply update(ObjectId id, Supply supply) {
        Bson filter = Filters.eq("_id", id);

        Document updatedDocument = new Document()
                .append("supply_status", supply.getSupplyStatus().value)
                .append("delivery_name", supply.getDeliveryName())
                .append("delivery_invoice_number", supply.getDeliveryInvoiceNumber())
                .append("delivery_total", new Decimal128(supply.getDeliveryTotal()))
                .append("received_at", supply.getReceivedAt() != null ?
                        supply.getReceivedAt().atZone(ZoneId.systemDefault()).toInstant() : null)
                .append("shoe_pair_info_supply", mapShoePairInfoSupplyList(supply.getShoePairInfoSupplyList()));

        Document updateOperation = new Document("$set", updatedDocument);

        supplyCollection.updateOne(filter, updateOperation);

        return findById(id).orElseThrow(() -> new IllegalStateException("Updated supply not found"));
    }

    private List<Document> mapShoePairInfoSupplyList(List<ShoePairInfoSupply> shoePairInfoSupplyList) {
        List<Document> documents = new ArrayList<>();
        for (ShoePairInfoSupply shoePairInfoSupply : shoePairInfoSupplyList) {
            Document shoePairInfoSupplyDoc = new Document()
                    .append("_id", shoePairInfoSupply.getId() != null ? shoePairInfoSupply.getId() : new ObjectId())
                    .append("site_url", shoePairInfoSupply.getSiteUrl())
                    .append("site_name", shoePairInfoSupply.getSiteName())
                    .append("shoe", mapShoe(shoePairInfoSupply.getShoe()))
                    .append("shoe_pair_info", mapShoePairInfoList(shoePairInfoSupply.getShoePairInfoList()));
            documents.add(shoePairInfoSupplyDoc);
        }
        return documents;
    }

    private Document mapShoe(Shoe shoe) {
        Document shoeDoc = new Document()
                .append("_id", shoe.getId() != null ? shoe.getId() : new ObjectId())
                .append("original_shoe_id", shoe.getOriginalShoeId())
                .append("name", shoe.getName())
                .append("model", shoe.getModel())
                .append("description", shoe.getDescription())
                .append("created_at", shoe.getCreatedAt() != null ?
                        shoe.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant() : null)
                .append("manufacturer_name", shoe.getManufacturerName())
                .append("shoe_type", shoe.getShoeType().value);

        if (shoe instanceof Heels heels) {
            shoeDoc.append("sole_material", heels.getSoleMaterial())
                    .append("insole_material", heels.getInsoleMaterial())
                    .append("inside_material", heels.getInsideMaterial())
                    .append("top_material", heels.getTopMaterial());
        } else if (shoe instanceof Sneakers sneakers) {
            shoeDoc.append("lacing_type", sneakers.getLacingType())
                    .append("sole_material", sneakers.getSoleMaterial())
                    .append("insole_material", sneakers.getInsoleMaterial())
                    .append("inside_material", sneakers.getInsideMaterial())
                    .append("top_material", sneakers.getTopMaterial());
        } else if (shoe instanceof Slippers slippers) {
            shoeDoc.append("sole_material", slippers.getSoleMaterial())
                    .append("insole_material", slippers.getInsoleMaterial())
                    .append("material", slippers.getMaterial());
        }

        return shoeDoc;
    }

    private List<Document> mapShoePairInfoList(List<ShoePairInfo> shoePairInfoList) {
        List<Document> documents = new ArrayList<>();
        for (ShoePairInfo shoePairInfo : shoePairInfoList) {
            Document shoePairInfoDoc = new Document()
                    .append("_id", shoePairInfo.getId() != null ? shoePairInfo.getId() : new ObjectId())
                    .append("original_shoe_pair_info_id", shoePairInfo.getOriginalShoePairInfoId())
                    .append("color", shoePairInfo.getColor())
                    .append("size", shoePairInfo.getSize())
                    .append("article", shoePairInfo.getArticle())
                    .append("count", shoePairInfo.getCount())
                    .append("code", shoePairInfo.getCodeList())
                    .append("price", new Decimal128(shoePairInfo.getPrice()));
            documents.add(shoePairInfoDoc);
        }
        return documents;
    }
}
