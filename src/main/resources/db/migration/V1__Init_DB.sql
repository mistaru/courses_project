create table daily_report (id  bigserial not null, date date, dish_price bytea, dish_quantity bytea, profits float4 not null, user_id int8, primary key (id));

create table dish (id  bigserial not null, name varchar(255), price float4 not null, primary key (id));

create table orderr (id  bigserial not null, date timestamp, price float4 not null, status varchar(255), daily_report_id int8, restaurant_id int8, user_id int8, primary key (id));

create table orderr_products (order_model_id int8 not null, products int8 not null);

create table product (id  bigserial not null, price float4 not null, quantity int4 not null, dish int8, primary key (id));

create table restaurant (id  bigserial not null, address varchar(255), name varchar(255), primary key (id));

create table role (id  bigserial not null, name varchar(255), primary key (id));

create table userr (id  bigserial not null, first_name varchar(255), last_name varchar(255), password varchar(255) not null,
                    username varchar(255) not null, restaurant_id int8, primary key (id));

create table userr_roles (users int8 not null, roles int8 not null);

alter table restaurant add constraint UK_i6u3x7opncroyhd755ejknses unique (name);

alter table userr add constraint UK_n67vkjwdu5sqqnyg6m79gmvo8 unique (username);

alter table daily_report add constraint FK3reikq7vx0c2u4e3khn60irqb foreign key (user_id) references userr;

alter table orderr add constraint FKeixisnqy58ipyn7px1wnctsr8 foreign key (daily_report_id) references daily_report;

alter table orderr add constraint FKj2m2jddr502d4yipfvusritho foreign key (restaurant_id) references restaurant;

alter table orderr add constraint FK8usxajmg2nbbne3474kyvqknc foreign key (user_id) references userr;

alter table orderr_products add constraint FK7e38m7xcwbj5ddrty974u7guf foreign key (products) references product;

alter table orderr_products add constraint FKlwyo985eiqmh30ho85kc5ehog foreign key (order_model_id) references orderr;

alter table product add constraint FKn16m6k5j5gytdw0s497gob07x foreign key (dish) references dish;

alter table userr add constraint FKbb0cscaad3wlwnjk0wo38g54l foreign key (restaurant_id) references restaurant;

alter table userr_roles add constraint FK4podsm364usoj1ahqdhgbt9xe foreign key (roles) references role;

alter table userr_roles add constraint FKlocseyx0chsijsw3cx00e21ov foreign key (users) references userr;
