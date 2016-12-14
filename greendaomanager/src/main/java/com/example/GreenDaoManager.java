package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoManager {
    private static final String OUTPUT_DIR = "app/src/main/java";
    private static final String PACKAGE_NAME = "greendao";
    private static final int DATABASE_VERSION = 1;

    private static final String PRODUCER_TABLE = "ProducerData";
    private static final String PRODUCER_COLUMN_ID = "producerId";
    private static final String PRODUCER_COLUMN_NAME = "producerName";
    private static final String PRODUCER_COLUMN_LOCATION = "producerLocation";
    private static final String PRODUCER_COLUMN_DESCRIPTION = "producerDescription";
    private static final String PRODUCER_COLUMN_IMAGE_URL = "producerImageURL";

    public static void main(String[] args) {
        Schema schema = new Schema(DATABASE_VERSION, PACKAGE_NAME);
        createTables(schema);

        try {
            new DaoGenerator().generateAll(schema, OUTPUT_DIR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTables(final Schema schema) {
        Entity user = addProducer(schema);

        // Add tables relationships if needed
    }

    private static Entity addProducer(final Schema schema) {
        Entity producer = schema.addEntity(PRODUCER_TABLE);
        producer.addIdProperty().primaryKey().autoincrement();
        producer.addLongProperty(PRODUCER_COLUMN_ID).notNull().unique();
        producer.addStringProperty(PRODUCER_COLUMN_NAME);
        producer.addStringProperty(PRODUCER_COLUMN_IMAGE_URL);
        producer.addStringProperty(PRODUCER_COLUMN_LOCATION);
        producer.addStringProperty(PRODUCER_COLUMN_DESCRIPTION);

        return producer;
    }

}
