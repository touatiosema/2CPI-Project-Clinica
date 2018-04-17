CREATE TABLE  personne(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nom VARCHAR(50),
	prenom VARCHAR(50),   
    address VARCHAR(50),
    telephone VARCHAR(10),
    genre CHAR,
    dateDeNaissance DATE
);




CREATE TABLE medecin (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_personne INT,
    username VARCHAR(50),
    password VARCHAR(50),
    active BOOLEAN,
    FOREIGN KEY(id_personne) REFERENCES personne(id)
);



/* Patients table */
CREATE TABLE patients (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_personne INT,
    synopsis VARCHAR(1000),
    profession VARCHAR(50),
    lieuDeTravail VARCHAR(50),
    groupage VARCHAR(3),
    taille INT,
    FOREIGN KEY(id_personne) REFERENCES personne(id)
);


/* Agenda table */
CREATE TABLE agenda(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    date date,
    time time,
    patient VARCHAR(50),
    description VARCHAR(200),
    type CHAR
);


CREATE TABLE agenda_medecin (
	/*id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),*/
	id_rdv int,
	id_medecin int,
	id_patient int,
    FOREIGN KEY(id_medecin) REFERENCES medecin(id),    
    FOREIGN KEY(id_rdv) REFERENCES agenda(id)

);









/* Consultations table */
CREATE TABLE consultations (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    poids INT,
    motif VARCHAR(200),
    exam_clin VARCHAR(200),
    exam_supl VARCHAR(200),
    diagnostic VARCHAR(200),
    lettre_dorientation VARCHAR(1000),
    certificat VARCHAR(1000),
    date date,
    time time
);



CREATE TABLE consultaions_medecin_patient (
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	id_medecin int,
	id_consultation int,
	id_patient int,
    FOREIGN KEY(id_medecin) REFERENCES medecin(id),
    FOREIGN KEY(id_consultation) REFERENCES consultations(id),
    FOREIGN KEY(id_patient) REFERENCES patients(id)
);


/* Meds table */
CREATE TABLE medicament (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nom_commercial VARCHAR(50),
    nom_scientifique VARCHAR(100),
    type VARCHAR(30)
);


/* Meds_in_consultations table */
CREATE TABLE ligne_ordonnance (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_medicament INT,
    detail VARCHAR(100),
    doze VARCHAR(50),
    FOREIGN KEY(id_medicament) REFERENCES medicament(id)
);


CREATE TABLE ordonnance(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	id_consultation int,
	id_ligne_ordonnance int, 
			
    FOREIGN KEY(id_ligne_ordonnance) REFERENCES ligne_ordonnance(id),
    FOREIGN KEY(id_consultation) REFERENCES consultations(id)
);


/* Exams */
CREATE TABLE exams (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    examen VARCHAR(50),
    typeExamen VARCHAR(50)
);



/* Exams_in_consultations */
CREATE TABLE exams_consultation (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_consultation INT,
    id_examen INT,
    FOREIGN KEY(id_consultation) REFERENCES consultations(id),
    FOREIGN KEY(id_examen) REFERENCES exams(id)
);


-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('Andre', 'Vesle',     'cite2','553895632','M','1966-06-21 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('Realdo', 'Colombo',  'cite1','656839223','M','1964-12-19 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('Andrea', 'Cesalpino','cite3','558749586','M','1986-02-21 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('William', 'Harvey',  'cite4','651968475','M','1923-12-13 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('Gaspare', 'Aselli',  'cite5','798961974','M','2013-08-29 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('Mrealdo', 'dolombo',  'cite6','656839223','M','1964-12-19 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('mandre', 'desle',     'cite7','553895632','M','1966-06-21 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('mandrea', 'desalpino','cite8','558749586','M','1986-02-21 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('milliam', 'darvey',  'cite9','651968475','M','1923-12-13 00:00:00');
-- INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES ('maspare', 'dselli',  'cite10','798961974','M','2013-08-29 00:00:00');

-- INSERT INTO medecin(id_personne, username, password, active) VALUES (6,'user1','passwd', true);
-- INSERT INTO medecin(id_personne, username, password, active) VALUES (7,'user2','passwd', true);
-- INSERT INTO medecin(id_personne, username, password, active) VALUES (8,'user3','passwd', true);
-- INSERT INTO medecin(id_personne, username, password, active) VALUES (9,'user4','passwd', true);
-- INSERT INTO medecin(id_personne, username, password, active) VALUES (10,'user5','passwd', true);

-- INSERT INTO patients(id_personne, synopsis, profession, lieuDeTravail, groupage, taille) VALUES (1, 'here is synopsis', 'enseignant','Alger Centre','O+', 180);
-- INSERT INTO patients(id_personne, synopsis, profession, lieuDeTravail, groupage, taille) VALUES (2, 'here is synopsis', 'pilote','Rouiba','AB-', 160);
-- INSERT INTO patients(id_personne, synopsis, profession, lieuDeTravail, groupage, taille) VALUES (3, 'here is synopsis', 'chercheur','Hussein Dey','B+', 175);
-- INSERT INTO patients(id_personne, synopsis, profession, lieuDeTravail, groupage, taille) VALUES (4, 'here is synopsis', 'controlleur','Birtouta','B+', 166);
-- INSERT INTO patients(id_personne, synopsis, profession, lieuDeTravail, groupage, taille) VALUES (5, 'here is synopsis', 'hacker','Houssin Dey','B-', 168);

-- INSERT INTO agenda(date, time, patient, description, type) VALUES ('2017-08-18 00:00:00','14:00','farid touati',NULL,'F');
-- INSERT INTO agenda(date, time, patient, description, type) VALUES ('2017-05-18 00:00:00', '15:00','farid touati','Pour l''offre du stage','F');
-- INSERT INTO agenda(date, time, patient, description, type) VALUES ('2017-09-18 00:00:00','8:00','farid touati',NULL,'F');
-- INSERT INTO agenda(date, time, patient, description, type) VALUES ('2017-07-18 00:00:00','14:00','farid touati',NULL,'R');
-- INSERT INTO agenda(date, time, patient, description, type) VALUES ('2017-10-18 00:00:00', '15:00','farid touati','Pour l''offre du stage','R');
-- INSERT INTO agenda(date, time, patient, description, type) VALUES ('2017-09-18 00:00:00','8:00','farid touati',NULL,'R');

-- INSERT INTO agenda_medecin(id_medecin, id_patient, id_rdv) VALUES (1,2,1);
-- INSERT INTO agenda_medecin(id_medecin, id_patient, id_rdv) VALUES (2,3,2);
-- INSERT INTO agenda_medecin(id_medecin, id_patient, id_rdv) VALUES (1,2,3);
-- INSERT INTO agenda_medecin(id_medecin, id_patient, id_rdv) VALUES (2,NULL,4);
-- INSERT INTO agenda_medecin(id_medecin, id_patient, id_rdv) VALUES (1,NULL,5);
-- INSERT INTO agenda_medecin(id_medecin, id_patient, id_rdv) VALUES (1,NULL,6);


-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (78, 'Fievre, Dyshagie, Asthemie','Examen de la gorge à d''un abaisse langue',NULL,'Angine purulente',NULL,'Je sous-signé avoir examiner le sus-nomé qui présente une angine purulente nécessitant un repos de 4 jours','2017-04-10 00:00:00','10:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (72, 'Brulures mictionnelles, Pollakiurie, Dysurie','Palpation de l''abdomen','Chimie des urines','Infection urunaire',NULL,NULL,'2017-08-18 00:00:00','14:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (65, 'Bouton au niveau du pied','Inspection du pied',NULL,'Furoncle',NULL,NULL,'2017-01-04 00:00:00','15:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (70, 'Subictere','Palpation de l''abdomen',NULL,'Hepatite virale','some path',NULL,'2017-03-08 00:00:00','11:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (80, 'Douleurs articulaires, rougeurs au niveau des genoux + oedeume','Inspection, Palpation',NULL,'Arthrite des genoux',NULL,NULL,'2017-03-17 00:00:00','13:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (75, 'Toux, Fievre, Alternation de l''etat general','Percussion du thorax, Auscultation du poumon',NULL,'Bronchite aigue',NULL,NULL,'2017-09-05 00:00:00','9:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (83, 'Otalgies, Fievre, Ecoulement purulent','Examen des oreilles a l''aide d''un otoscope',NULL,NULL,NULL,NULL,'2017-06-03 00:00:00','8:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (79, 'Ecoulement nasal, Cephalées','Examen du nez','Radio du sinus','Sinusité',NULL,NULL,'2017-03-17 00:00:00','15:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (30, 'Douleurs abdomunales, Constipation','Palpation de l''abdomen',NULL,'Colopathie',NULL,NULL,'2017-09-05 00:00:00','11:00');
-- INSERT INTO consultations(poids, motif, exam_clin, exam_supl, diagnostic, lettre_dorientation, certificat, date, time) VALUES (82,'Fievre, Alternation de l''etat generale, Raideur de la nuque, Vomissement','Examende de la gorge, des oreilles et de la nuque ',NULL,'Méningite','Cher confrere,\nJe vous adesse le patient sus-nomé qui presente une suspection d''une Meningite .\nJe vous le confie pour une prise en charge rn milieu hospitalié.\nConfraterenellement',NULL,'2017-09-15 00:00:00','12:00');

-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (1,1,1);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (1,2,2);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (2,3,3);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (2,4,4);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (3,5,5);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (4,1,6);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (5,2,7);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (5,3,8);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (1,4,9);
-- INSERT INTO consultaions_medecin_patient(id_medecin, id_patient, id_consultation) VALUES (1,5,10);

-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Peniciline G','benzylpénicilline','injectable');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Nifluril','Acide Niflumique','comprime');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Catalgine','Acetylsalicylate de sodium','sachet');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Nibiol','Nitroxoline','comprime');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Bactrim','Co-trimoxazole','comprime');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Roxid Gel','Roxithromycin','gel');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Zecuf','Phytotherapie','sirop');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Doliprane','Paracetamol','comprime');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Bristopen Injectable','Oxacilline','injectable');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Betadine Sol','Povidone-iodine','solution');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Voltarene','Diclofenac Soduim','comprime');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Doliprane','Paracetamol','comprime');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Augmentin','Amoxicillin Clavulanate','sachet');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Oxygené','Oxygené','solution');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Céfacidal','Cefazoline','injectable');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Perfalgon','Paracetamol','injectable');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Amoclan','Amoxicillin Clavulanate','injectable');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Pepsane','Dimeticone','sachet');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Spasfon','Phloroglucinol','comprime');
-- INSERT INTO medicament(nom_commercial, nom_scientifique, type) VALUES ('Duphalac','Lactulose','sirop');

-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (20,'3/jours',NULL);
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (2,'1/jour',NULL);
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (3,'3/jours','0.5mg');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (4,'2*3/jours','200mg');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (5,'3/jours',NULL);
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (6,'2/jours','150mg');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (7,'3/jours',NULL);
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (8,'3/jours','1g');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (9,'2/jours pendant 10 jours','1g');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (10,'2/jours','100ml');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (12,'1/jour pendant 10 jours','1m');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (11,'1/jour',NULL);
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (3,'3/jours','0.5mg');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (13,'2*3/jours','500mg');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (14,'3/jours',NULL);
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (15,'2/jours','150mg');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (16,'3/jours',NULL);
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (17,'3/jours','1g');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (2,'2/jours pendant 10 jours','1g');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (18,'2/jours','100ml');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (19,'2/jours','150mg');
-- INSERT INTO ligne_ordonnance(id_medicament, detail, doze) VALUES (20,'3/jours',NULL);

-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (1,1);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (1,2);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (1,3);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (2,4);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (2,5);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (6,6);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (6,7);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (6,8);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (3,9);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (3,10);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (5,11);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (5,12);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (7,13);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (7,14);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (7,15);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (7,16);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (8,17);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (8,18);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (8,19);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (9,20);
-- INSERT INTO ordonnance(id_consultation, id_ligne_ordonnance) VALUES (9,21);

-- INSERT INTO exams(examen, typeExamen) VALUES ('ECBU', 'biologique');
-- INSERT INTO exams(examen, typeExamen) VALUES ('FNS', 'sanguin');
-- INSERT INTO exams(examen, typeExamen) VALUES ('Telethorax', 'biologique');
-- INSERT INTO exams(examen, typeExamen) VALUES ('HBS', 'sanguin');
-- INSERT INTO exams(examen, typeExamen) VALUES ('Echographie hépatique', 'sanguin');
-- INSERT INTO exams(examen, typeExamen) VALUES ('RX des genoux et profil', 'radiologique');
-- INSERT INTO exams(examen, typeExamen) VALUES ('VS ASLO', 'extension');
-- INSERT INTO exams(examen, typeExamen) VALUES ('Ponction Lombaire', 'extension');
-- INSERT INTO exams(examen, typeExamen) VALUES ('ASP', 'biologique');

-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (2,1);
-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (2,2);
-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (3,3);
-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (5,4);
-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (5,5);
-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (6,6);
-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (6,7);
-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (8,8);
-- INSERT INTO exams_consultation (id_consultation, id_examen) VALUES (8,9);
