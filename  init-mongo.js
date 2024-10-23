db = db.getSiblingDB('kitchensink');

db.dropDatabase();
db.createCollection('members');
db.members.createIndex({ email: 1 }, { unique: true });

db.members.insertOne({
    name: "HBT",
    email: "hbt@example.com",
    phoneNumber: "1234567890"
});