SET FOREIGN_KEY_CHECKS=0;




CREATE TABLE emp_form
(
	fid INTEGER NOT NULL,
	acid INTEGER,
	field VARCHAR(200),
	fintroduction TEXT,
	lmessage TEXT,
	tid INTEGER,
	PRIMARY KEY (fid)
) 
;


CREATE TABLE emp_form_view
(
	fvid INTEGER NOT NULL,
	fid INTEGER,
	fv_title VARCHAR(200),
	fv_type TINYINT,
	fv_required TINYINT,
	PRIMARY KEY (fvid)
) 
;


CREATE TABLE emp_list_form
(
	lid INTEGER NOT NULL,
	fid INTEGER,
	lform_text TEXT,
	mobile_id VARCHAR(200),
	lpost_time DATETIME,
	PRIMARY KEY (lid)
) 
;



SET FOREIGN_KEY_CHECKS=1;
