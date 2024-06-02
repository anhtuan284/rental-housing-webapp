use rentaldb;
INSERT INTO `User` VALUES (2,'https://res.cloudinary.com/dgsii3nt1/image/upload/v1701079653/LOGO_ao7prq.png','admin','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','213@gmail.com','2024-05-14',1,'Huy','23123123','23123123',NULL,'2024-05-13 23:56:25','2024-05-27 16:31:08');
INSERT INTO `TypeOfPost` VALUES (1,'Cho thuê'),(2,'Muốn thuê');
INSERT INTO `Post` VALUES (1,'ccccc','ádas',1,1,2,'2024-05-13 23:56:25','2024-05-29 05:42:48');
INSERT INTO `Notification` VALUES (1,2,1,'đsad',1,'2024-05-13 23:56:25');

INSERT INTO `Property_Detail` VALUES (1,'2000-01-23 00:00:00','122222','106',22,1);
INSERT INTO `Image` VALUES (1,1,'https://res.cloudinary.com/dgsii3nt1/image/upload/v1701079653/LOGO_ao7prq.png','2024-05-13 23:56:25'),(2,1,'https://res.cloudinary.com/dwvkjyixu/image/upload/v1715070734/lcxgvm5qopqi9gjcoj3o.png','2024-05-13 23:56:25'),(3,1,'https://res.cloudinary.com/dwvkjyixu/image/upload/v1715070734/lcxgvm5qopqi9gjcoj3o.png','2024-05-13 23:56:25');

-- Insert into User table
INSERT INTO `User` VALUES 
(3,'https://images.unsplash.com/photo-1511367461989-f85a21fda167','user1','$2a$10$7Y9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','user1@gmail.com','2024-05-14',1,'John','23456789','23456789',NULL,'2024-05-14 09:10:25','2024-05-27 10:30:08'),
(4,'https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d','user2','$2a$10$9W9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','user2@gmail.com','2024-05-15',1,'Jane','34567890','34567890',NULL,'2024-05-15 08:20:25','2024-05-28 11:30:08'),
(5,'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d','user3','$2a$10$8X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','user3@gmail.com','2024-05-16',1,'Alice','45678901','45678901',NULL,'2024-05-16 07:15:25','2024-05-29 12:30:08');



-- Insert into Post table
INSERT INTO `Post` VALUES 
(2,'Apartment for rent','Nice and cozy apartment',1,1,3,'2024-05-14 09:10:25','2024-05-30 06:30:08'),
(3,'Looking for roommate','Friendly and clean',1,1,3,'2024-05-15 08:20:25','2024-05-31 07:30:08'),
(4,'House for rent','Spacious house',1,1,4,'2024-05-16 07:15:25','2024-06-01 08:30:08');

-- Insert into Notification table
INSERT INTO `Notification` VALUES 
(2,3,2,'New post available',1,'2024-05-14 09:10:25'),
(3,4,2,'Roommate needed',1,'2024-05-15 08:20:25'),
(4,5,3,'House for rent',1,'2024-05-16 07:15:25');

-- Insert into Property_Detail table
INSERT INTO `Property_Detail` VALUES 
(2,'2000-02-24 00:00:00','150000','200',30,2),
(3,'2000-03-25 00:00:00','130000','150',25,3),
(4,'2000-04-26 00:00:00','170000','250',35,4);

-- Insert into Image table
INSERT INTO `Image` VALUES 
(4,2,'https://images.unsplash.com/photo-1560185127-6d8d7885f99e','2024-05-14 09:10:25'),
(5,2,'https://images.unsplash.com/photo-1554995207-c18c203602cb','2024-05-14 09:10:25'),
(6,3,'https://images.unsplash.com/photo-1600585154340-be6161c99c9a','2024-05-15 08:20:25'),
(7,3,'https://images.unsplash.com/photo-1600585153808-0f0b7a4b5f87','2024-05-15 08:20:25'),
(8,4,'https://images.unsplash.com/photo-1597004923751-0c62aef78c7d','2024-05-16 07:15:25'),
(9,4,'https://images.unsplash.com/photo-1572120360610-d971b9c8e5d8','2024-05-16 07:15:25');
