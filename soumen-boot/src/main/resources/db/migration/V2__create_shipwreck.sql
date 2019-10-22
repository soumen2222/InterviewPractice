CREATE TABLE SHIPWRECK(
	ID BIGINT AUTO_INCREMENT,
	FIRSTNAME VARCHAR(255),
	DESCRIPTION VARCHAR(2000),
	COND VARCHAR(255),
	DEPTH INT,
	LATITUDE DOUBLE,
	LONGITUDE DOUBLE,
	YEAR_DISCOVERED INT,
    primary key (ID)
)