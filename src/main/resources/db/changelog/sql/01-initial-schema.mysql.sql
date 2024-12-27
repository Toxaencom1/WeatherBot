-- liquibase formatted sql

-- changeset TaXaH:1734789970892-1
CREATE TABLE city
(
    country_id BIGINT                NULL,
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NULL,
    CONSTRAINT PK_CITY PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-2
CREATE TABLE cloud_cover
(
    total INT                   NOT NULL,
    id    BIGINT AUTO_INCREMENT NOT NULL,
    CONSTRAINT PK_CLOUD_COVER PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-3
CREATE TABLE country
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT PK_COUNTRY PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-4
CREATE TABLE current_weather
(
    cloud_cover      INT                   NOT NULL,
    icon_num         INT                   NOT NULL,
    temperature      DOUBLE                NOT NULL,
    id               BIGINT AUTO_INCREMENT NOT NULL,
    precipitation_id BIGINT                NULL,
    wind_id          BIGINT                NULL,
    icon             VARCHAR(255)          NULL,
    summary          VARCHAR(255)          NULL,
    CONSTRAINT PK_CURRENT_WEATHER PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-5
CREATE TABLE hourly_data
(
    icon             INT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         NOT NULL,
    temperature      DOUBLE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      NOT NULL,
    cloud_cover_id   BIGINT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      NULL,
    date             datetime(6)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 NULL,
    id               BIGINT AUTO_INCREMENT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       NOT NULL,
    precipitation_id BIGINT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      NULL,
    wind_id          BIGINT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      NULL,
    weather          VARCHAR(255)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                NULL,
    summary          ENUM ('CLEAR', 'CLEAR_NIGHT', 'CLOUDY', 'CLOUDY_NIGHT', 'FOG', 'FREEZING_RAIN', 'HAIL', 'LIGHT_RAIN', 'LIGHT_SNOW', 'LOCAL_THUNDERSTORMS', 'LOCAL_THUNDERSTORMS_NIGHT', 'MOSTLY_CLEAR', 'MOSTLY_CLEAR_NIGHT', 'MOSTLY_CLOUDY', 'MOSTLY_CLOUDY_NIGHT', 'MOSTLY_SUNNY', 'NOT_AVAILABLE', 'OVERCAST', 'OVERCAST_LOW_CLOUDS', 'OVERCAST_LOW_CLOUDS_NIGHT', 'PARTLY_CLEAR', 'PARTLY_CLEAR_NIGHT', 'PARTLY_SUNNY', 'POSSIBLE_FREEZING_RAIN', 'POSSIBLE_RAIN', 'POSSIBLE_RAIN_AND_SNOW', 'POSSIBLE_RAIN_AND_SNOW_NIGHT', 'POSSIBLE_SNOW', 'RAIN', 'RAIN_AND_SNOW', 'RAIN_AND_SNOW_NIGHT', 'RAIN_SHOWER', 'RAIN_SHOWER_NIGHT', 'SNOW', 'SNOW_SHOWER', 'SNOW_SHOWER_NIGHT', 'SUNNY', 'THUNDERSTORM') NULL,
    CONSTRAINT PK_HOURLY_DATA PRIMARY KEY (id),
    UNIQUE (cloud_cover_id),
    UNIQUE (precipitation_id),
    UNIQUE (wind_id)
);

-- changeset TaXaH:1734789970892-6
CREATE TABLE hourly_weather
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    CONSTRAINT PK_HOURLY_WEATHER PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-7
CREATE TABLE hourly_weather_data
(
    data_id           BIGINT NOT NULL,
    hourly_weather_id BIGINT NOT NULL
);

-- changeset TaXaH:1734789970892-8
CREATE TABLE location
(
    city_id    BIGINT                NULL,
    country_id BIGINT                NULL,
    id         BIGINT AUTO_INCREMENT NOT NULL,
    CONSTRAINT PK_LOCATION PRIMARY KEY (id),
    UNIQUE (city_id)
);

-- changeset TaXaH:1734789970892-9
CREATE TABLE precipitation
(
    total DOUBLE                                                                   NOT NULL,
    id    BIGINT AUTO_INCREMENT                                                    NOT NULL,
    type  ENUM ('FROZEN_RAIN', 'ICE_PELLETS', 'NONE', 'RAIN', 'RAIN_SNOW', 'SNOW') NOT NULL,
    CONSTRAINT PK_PRECIPITATION PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-10
CREATE TABLE subscriber
(
    chat_id     BIGINT                NULL,
    id          BIGINT AUTO_INCREMENT NOT NULL,
    location_id BIGINT                NULL,
    name        VARCHAR(255)          NULL,
    CONSTRAINT PK_SUBSCRIBER PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-11
CREATE TABLE weather_response_data
(
    created_day        date                  NULL,
    elevation          INT                   NOT NULL,
    current_weather_id BIGINT                NULL,
    hourly_weather_id  BIGINT                NULL,
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    lat                VARCHAR(255)          NULL,
    lon                VARCHAR(255)          NULL,
    timezone           VARCHAR(255)          NULL,
    units              VARCHAR(255)          NULL,
    CONSTRAINT PK_WEATHER_RESPONSE_DATA PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-12
CREATE TABLE wind
(
    angle INT                   NOT NULL,
    speed DOUBLE                NOT NULL,
    id    BIGINT AUTO_INCREMENT NOT NULL,
    dir   VARCHAR(255)          NULL,
    CONSTRAINT PK_WIND PRIMARY KEY (id)
);

-- changeset TaXaH:1734789970892-13
CREATE INDEX FKhwe3ogb36m1di0eijw7s7cua ON hourly_weather_data (hourly_weather_id);

-- changeset TaXaH:1734789970892-14
CREATE INDEX FKrpd7j1p7yxr784adkx4pyepba ON city (country_id);

-- changeset TaXaH:1734789970892-15
CREATE UNIQUE INDEX UK440o7qptpm56nc00pcl2vecj3 ON current_weather (precipitation_id);

-- changeset TaXaH:1734789970892-16
CREATE UNIQUE INDEX UKbowuw6jbu11ciuy4mai1nfhf6 ON weather_response_data (hourly_weather_id);

-- changeset TaXaH:1734789970892-17
CREATE UNIQUE INDEX UKiwba594qmw0654bkduu64wpwd ON current_weather (wind_id);

-- changeset TaXaH:1734789970892-18
CREATE UNIQUE INDEX UKm85ykplv4jfmmi7u8rokmuvk4 ON weather_response_data (current_weather_id);

-- changeset TaXaH:1734789970892-19
CREATE UNIQUE INDEX UKmkpe6xom1hhuj1b86v8c5dmy9 ON hourly_weather_data (data_id);

# -- changeset TaXaH:1734789970892-20
# CREATE UNIQUE INDEX UKnhf843alxhax27wl7pdt87xrb ON subscriber (location_id);

-- changeset TaXaH:1734789970892-21
# CREATE UNIQUE INDEX UKoij09nrgw3jac87nxirlho8cj ON location (country_id);

-- changeset TaXaH:1734789970892-22
ALTER TABLE weather_response_data
    ADD CONSTRAINT FK56xqxf20otnt82twnchiamwqo FOREIGN KEY (hourly_weather_id) REFERENCES hourly_weather (id) ON UPDATE RESTRICT ON DELETE CASCADE ;

-- changeset TaXaH:1734789970892-23
ALTER TABLE current_weather
    ADD CONSTRAINT FK7esr9vn2efdtojqpcno3o50o1 FOREIGN KEY (wind_id) REFERENCES wind (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-24
ALTER TABLE current_weather
    ADD CONSTRAINT FK82889ra11cv6fonhx1c6v955o FOREIGN KEY (precipitation_id) REFERENCES precipitation (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-25
ALTER TABLE hourly_data
    ADD CONSTRAINT FKau2jeq7e730872dprd3at00m8 FOREIGN KEY (precipitation_id) REFERENCES precipitation (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-26
ALTER TABLE hourly_data
    ADD CONSTRAINT FKbjq24odn8bhjxk1ufxhcdo77d FOREIGN KEY (wind_id) REFERENCES wind (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-27
ALTER TABLE hourly_data
    ADD CONSTRAINT FKer2jrqjh7shimtc60v2at3tnp FOREIGN KEY (cloud_cover_id) REFERENCES cloud_cover (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-28
ALTER TABLE hourly_weather_data
    ADD CONSTRAINT FKhwe3ogb36m1di0eijw7s7cua FOREIGN KEY (hourly_weather_id) REFERENCES hourly_weather (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-29
ALTER TABLE location
    ADD CONSTRAINT FKn5m6ve3ryy2r25qvisdrg0aos FOREIGN KEY (country_id) REFERENCES country (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-30
ALTER TABLE location
    ADD CONSTRAINT FKr2gdhhu8rhyrvslukhtfbg8v5 FOREIGN KEY (city_id) REFERENCES city (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-31
ALTER TABLE city
    ADD CONSTRAINT FKrpd7j1p7yxr784adkx4pyepba FOREIGN KEY (country_id) REFERENCES country (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-32
ALTER TABLE hourly_weather_data
    ADD CONSTRAINT FKstf5rjdcjwxdaiiwihakg8nbo FOREIGN KEY (data_id) REFERENCES hourly_data (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-33
ALTER TABLE weather_response_data
    ADD CONSTRAINT FKtathb3yqm08ppj52vcghipdhp FOREIGN KEY (current_weather_id) REFERENCES current_weather (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset TaXaH:1734789970892-34
ALTER TABLE subscriber
    ADD CONSTRAINT FKtqnh75ik28dj63b1et4ws9lqb FOREIGN KEY (location_id) REFERENCES location (id) ON UPDATE RESTRICT ON DELETE CASCADE;

