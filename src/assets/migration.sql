CREATE TABLE  personne(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nom VARCHAR(50),
	prenom VARCHAR(50),   
    address VARCHAR(50),
    telephone VARCHAR(10),
    genre CHAR,
    dateDeNaissance DATE
);

CREATE TABLE Config(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    config_key VARCHAR(50),
	  config_value VARCHAR(50)
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
    type CHAR,
    remindingtime int
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