/* Doctors Table */
CREATE TABLE doctors (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50),
    password VARCHAR(50),
    name VARCHAR(50),
    phone VARCHAR(10)
);
INSERT INTO doctors(username, password, name, phone) VALUES ('user1','passwd','Realdo Colombo','656839223');
INSERT INTO doctors(username, password, name, phone) VALUES ('user2','passwd','Andre Vesle','553895632');
INSERT INTO doctors(username, password, name, phone) VALUES ('user3','passwd','Andrea Cesalpino','558749586');
INSERT INTO doctors(username, password, name, phone) VALUES ('user4','passwd','William Harvey','651968475');
INSERT INTO doctors(username, password, name, phone) VALUES ('user5','passwd','Gaspare Aselli','798961974');

/* Patients table */
CREATE TABLE patients (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(50),
    birthdate date,
    address VARCHAR(50),
    phone VARCHAR(10),
    gender CHAR,
    description VARCHAR(1000),
    job VARCHAR(50),
    workplace VARCHAR(50),
    blood_type VARCHAR(3)
);
INSERT INTO patients(name, birthdate, address, phone, gender, description, job, workplace, blood_type) VALUES ('Alois Alzheimer','1964-12-19 00:00:00','Alger Centre','656839223','M',NULL,NULL,'Alger Centre','O+');
INSERT INTO patients(name, birthdate, address, phone, gender, description, job, workplace, blood_type) VALUES ('Charles Jules Henri','1966-06-21 00:00:00','Rouiba','553895632','M',NULL,NULL,'Rouiba','AB-');
INSERT INTO patients(name, birthdate, address, phone, gender, description, job, workplace, blood_type) VALUES ('Maria Montessori','1986-02-21 00:00:00','Hussein Dey','558749586','F',NULL,NULL,'Hussein Dey','B+');
INSERT INTO patients(name, birthdate, address, phone, gender, description, job, workplace, blood_type) VALUES ('Jules Bordet','1923-12-13 00:00:00','Birtouta','651968475','M',NULL,NULL,'Birtouta','B+');
INSERT INTO patients(name, birthdate, address, phone, gender, description, job, workplace, blood_type) VALUES ('Felix Deve','2013-08-29 00:00:00','El Harach','798961974','M',NULL,NULL,'El Harach','O-');


/* Agenda table */
CREATE TABLE agenda (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    doctor_id INT,
    patient_id INT,
    date date,
    time time,
    type CHAR,
    title VARCHAR(50),
    description VARCHAR(200),

    FOREIGN KEY(doctor_id) REFERENCES doctors(id),
    FOREIGN KEY(patient_id) REFERENCES patients(id)
);

INSERT INTO agenda(doctor_id, patient_id, date, time, type, title, description) VALUES (1,2,'2017-08-18 00:00:00','14:00','C','Consultation avec Patient',NULL);
INSERT INTO agenda(doctor_id, patient_id, date, time, type, title, description) VALUES (1,NULL,NULL,NULL,'P','Reunion avec PDG','Pour l''offre du stage');
INSERT INTO agenda(doctor_id, patient_id, date, time, type, title, description) VALUES (1,2,'2017-09-18 00:00:00','8:00','C','Control',NULL);

/* Consultations table */
CREATE TABLE consultations (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    doctor_id INT,
    patient_id INT,
    weight INT,
    height INT,
    motif VARCHAR(200),
    exam_clinical VARCHAR(200),
    exam_supl VARCHAR(200),
    diagnostic VARCHAR(200),
    orientation_letter VARCHAR(1000),
    certificat VARCHAR(1000),
    date date,
    time time,

    FOREIGN KEY(doctor_id) REFERENCES doctors(id),
    FOREIGN KEY(patient_id) REFERENCES patients(id)
);

INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (1,2,78,172,'Fievre, Dyshagie, Asthemie','Examen de la gorge à d''un abaisse langue',NULL,'Angine purulente',NULL,'Je sous-signé avoir examiner le sus-nomé qui présente une angine purulente nécessitant un repos de 4 jours','2017-04-10 00:00:00','10:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (1,2,72,173,'Brulures mictionnelles, Pollakiurie, Dysurie','Palpation de l''abdomen','Chimie des urines','Infection urunaire',NULL,NULL,'2017-08-18 00:00:00','14:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (1,3,65,165,'Bouton au niveau du pied','Inspection du pied',NULL,'Furoncle',NULL,NULL,'2017-01-04 00:00:00','15:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (1,3,70,165,'Subictere','Palpation de l''abdomen',NULL,'Hepatite virale','Cher confrere,\nJe vous adesse le patient sus-nomé qui presente une Hepatite virale.\nJe vous le confie pour une prise en charge meilleure.\nConfraterenellement',NULL,'2017-03-08 00:00:00','11:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (2,1,80,180,'Douleurs articulaires, rougeurs au niveau des genoux + oedeume','Inspection, Palpation',NULL,'Arthrite des genoux',NULL,NULL,'2017-03-17 00:00:00','13:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (2,2,75,173,'Toux, Fievre, Alternation de l''etat general','Percussion du thorax, Auscultation du poumon',NULL,'Bronchite aigue',NULL,NULL,'2017-09-05 00:00:00','9:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (3,1,83,180,'Otalgies, Fievre, Ecoulement purulent','Examen des oreilles a l''aide d''un otoscope',NULL,NULL,NULL,NULL,'2017-06-03 00:00:00','8:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (3,5,79,170,'Ecoulement nasal, Cephalées','Examen du nez','Radio du sinus','Sinusité',NULL,NULL,'2017-03-17 00:00:00','15:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (4,4,30,168,'Douleurs abdomunales, Constipation','Palpation de l''abdomen',NULL,'Colopathie',NULL,NULL,'2017-09-05 00:00:00','11:00');
INSERT INTO consultations(doctor_id, patient_id, weight, height, motif, exam_clinical, exam_supl, diagnostic, orientation_letter, certificat, date, time) VALUES (5,1,82,180,'Fievre, Alternation de l''etat generale, Raideur de la nuque, Vomissement','Examende de la gorge, des oreilles et de la nuque ',NULL,'Méningite','Cher confrere,\nJe vous adesse le patient sus-nomé qui presente une suspection d''une Meningite .\nJe vous le confie pour une prise en charge rn milieu hospitalié.\nConfraterenellement',NULL,'2017-09-15 00:00:00','12:00');

/* Meds table */
CREATE TABLE meds (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name_commercial VARCHAR(50),
    name_scientific VARCHAR(100),
    type VARCHAR(30)
);
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Peniciline G','benzylpénicilline','injectable');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Nifluril','Acide Niflumique','comprime');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Catalgine','Acetylsalicylate de sodium','sachet');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Nibiol','Nitroxoline','comprime');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Bactrim','Co-trimoxazole','comprime');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Roxid Gel','Roxithromycin','gel');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Zecuf','Phytotherapie','sirop');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Doliprane','Paracetamol','comprime');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Bristopen Injectable','Oxacilline','injectable');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Betadine Sol','Povidone-iodine','solution');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Voltarene','Diclofenac Soduim','comprime');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Doliprane','Paracetamol','comprime');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Augmentin','Amoxicillin Clavulanate','sachet');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Oxygené','Oxygené','solution');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Céfacidal','Cefazoline','injectable');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Perfalgon','Paracetamol','injectable');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Amoclan','Amoxicillin Clavulanate','injectable');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Pepsane','Dimeticone','sachet');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Spasfon','Phloroglucinol','comprime');
INSERT INTO meds(name_commercial, name_scientific, type) VALUES ('Duphalac','Lactulose','sirop');

/* Meds_in_consultations table */
CREATE TABLE meds_in_consultations (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    consultation_id INT,
    med_id INT,
    details VARCHAR(50),
    doze VARCHAR(50),

    FOREIGN KEY(consultation_id) REFERENCES consultations(id)
);

INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (1,1,'1/jour pendant 10 jours','1m');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (1,2,'1/jour',NULL);
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (1,3,'3/jours','0.5mg');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (2,4,'2*3/jours','200mg');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (2,5,'3/jours',NULL);
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (6,6,'2/jours','150mg');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (6,7,'3/jours',NULL);
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (6,8,'3/jours','1g');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (3,9,'2/jours pendant 10 jours','1g');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (3,10,'2/jours','100ml');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (5,12,'1/jour pendant 10 jours','1m');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (5,11,'1/jour',NULL);
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (7,3,'3/jours','0.5mg');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (7,13,'2*3/jours','500mg');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (7,14,'3/jours',NULL);
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (10,15,'2/jours','150mg');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (10,16,'3/jours',NULL);
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (8,17,'3/jours','1g');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (8,2,'2/jours pendant 10 jours','1g');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (9,18,'2/jours','100ml');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (9,19,'2/jours','150mg');
INSERT INTO meds_in_consultations(consultation_id, med_id, details, doze) VALUES (9,20,'3/jours',NULL);

/* Exams */
CREATE TABLE exams (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(50)
);
INSERT INTO exams(name) VALUES ('ECBU');
INSERT INTO exams(name) VALUES ('FNS');
INSERT INTO exams(name) VALUES ('Telethorax');
INSERT INTO exams(name) VALUES ('HBS');
INSERT INTO exams(name) VALUES ('Echographie hépatique');
INSERT INTO exams(name) VALUES ('RX des genoux et profil');
INSERT INTO exams(name) VALUES ('VS ASLO');
INSERT INTO exams(name) VALUES ('Ponction Lombaire');
INSERT INTO exams(name) VALUES ('ASP');


/* Exams_in_consultations */
CREATE TABLE exams_in_consultations (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    consultation_id INT,
    exam_id INT,

    FOREIGN KEY(consultation_id) REFERENCES consultations(id)
);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (2,1);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (2,2);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (3,3);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (5,4);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (5,5);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (6,6);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (6,7);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (8,8);
INSERT INTO exams_in_consultations(consultation_id, exam_id) VALUES (10,9);
