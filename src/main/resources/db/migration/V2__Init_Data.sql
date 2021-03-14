INSERT INTO userr (first_name,
                   last_name,
                   password,
                   username,
                   restaurant_id
                   ) values  ('admin','admin','admin','admin',null);


INSERT INTO role (name) values ('ADMIN'),('USER');

INSERT INTO userr_roles (users,roles) values ('1','1');

INSERT INTO restaurant (address,name) values ('ул. Фрунзе 123','Фрунзе');
INSERT INTO restaurant (address,name) values ('пр-т Масалиева 321','Ала - Тоо');