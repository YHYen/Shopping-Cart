create database cart_db;
use cart_db;
-- drop table if exists tb_user;
-- drop table if exists tb_product;
-- drop table if exists tb_cart;
-- drop table if exists tb_order;
-- drop table if exists tb_order_product;

-- create user table
create table tb_user(
	-- user id int primary auto increment
	id				int primary key auto_increment, 
    
    -- username varchar(16)
    username		varchar(16), 
    
    -- password varchar(30)
    password		varchar(30), 
    
    -- Type int(1)
    type			int, 
    
    -- phone number varchar(10)
    phone_number	varchar(10), 
    
    -- email varchar(50)
    email			varchar(50)
);

create table tb_user_profile(
	-- profile id 	int primary auto increment
	id				int primary key auto_increment, 
    
    -- user id		int
    user_id			int, 
    
    -- description	varchar(200)
    description		varchar(200), 
    
    -- image path	varchar(255)
    image_path		varchar(255)
);

-- add foreign key to tb_user_profile (user_id)
alter table tb_user_profile add CONSTRAINT fk_profile_user foreign key(user_id) references tb_user(id);

create table tb_product(
	-- product id int primary auto increment
    id				int primary key auto_increment, 
    
    -- product name varchar(20)
    product_name	varchar(20), 
    
    -- product price DECIMAL(10, 2)
    price			decimal(10, 2), 
    
    -- quantity int
    quantity 		int, 
    
    -- Seller id foreign key
    seller_id		int, 
    
    -- image id int foreign key
    image_path		varchar(255)
    
);

-- add foreign key to tb_product (user_id)
alter table tb_product add CONSTRAINT fk_product_user foreign key(seller_id) references tb_user(id);

create table tb_seller_image(
	-- image id		int
    id				int primary key auto_increment, 
    
    -- seller id	int
    seller_id		int, 
    
    -- image path	varchar(255)
    image_path		varchar(255)
);

-- add foreign key to tb_seller_image (user_id)
alter table tb_seller_image add CONSTRAINT fk_seller_user foreign key(seller_id) references tb_user(id);

create table tb_cart(
	-- cart id int primary auto increment
    id				int primary key auto_increment, 
    
    -- user id foreign key
    user_id			int, 
    
    -- product id foreign key
    product_id		int, 
    
    -- quantity int(2)
    quantity		int, 
    
    -- create time timestamp current_timestamp
    create_time			TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    
);

-- add foreign key to tb_cart (user_id)
alter table tb_cart add CONSTRAINT fk_cart_user foreign key(user_id) references tb_user(id);

-- add foreign key to tb_cart (product_id)
alter table tb_cart add CONSTRAINT fk_cart_product foreign key(product_id) references tb_product(id);

create table tb_order(
	-- user id int primary auto increment
    id					int primary key auto_increment, 
    
    -- user id foreign key
	user_id				int, 
    
    -- price decimal(15, 2)
	price				decimal(15, 2), 
    
    -- pay type int
    pay_type			int, 
    
    -- payment status int
    payment_status		int, 
    
    -- shipping status int
    shipping_status		int, 
    
    -- create time timestamp current_timestamp
    create_time			TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    
    -- delivery time timestamp current_timestamp on update current_timestamp
    delivery_time		TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP
);

-- add foreign key to tb_order (user_id)
alter table tb_order add CONSTRAINT fk_order_user foreign key(user_id) references tb_user(id);

create table tb_order_product(
	-- order_product table id int primary auto increment
    id				int primary key auto_increment, 
    
    -- order id foreign key
    order_id		int, 
    
    -- product id foreign key
    product_id		int, 
    
    -- quantity int(2)
    quantity		int
    
);

-- add foreign key to tb_order_product (order_id)
alter table tb_order_product add CONSTRAINT fk_order_product_order foreign key(order_id) references tb_order(id);

-- add foreign key to tb_order_product (product_id)
alter table tb_order_product add CONSTRAINT fk_order_product_product foreign key(product_id) references tb_product(id);