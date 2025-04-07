<h1>BackEnd del Project Work UniPegaso - De Filippis Antonio Pio - Matr: 0312300708 </h1>

E' possibile utilizzare il seguente account demo, o crearne di nuovi.
Username: DemoPegaso
Password: Pegaso1234

Di seguito gli script utilizzati per la creazione delle tabelle:

-- Tabella Regione
CREATE TABLE Regione (
    id_regione SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_stato INT NOT NULL,
    FOREIGN KEY (id_stato) REFERENCES Stato(id) ON DELETE CASCADE
);

-- Tabella Provincia
CREATE TABLE Provincia (
    id_provincia SERIAL PRIMARY KEY,
    sigla CHAR(2) NOT NULL UNIQUE,
    id_regione INT NOT NULL,
    FOREIGN KEY (id_regione) REFERENCES Regione(id) ON DELETE CASCADE
);

-- Tabella Città
CREATE TABLE Citta (
    id_citta SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_provincia INT NOT NULL,
    FOREIGN KEY (id_provincia) REFERENCES Provincia(id) ON DELETE CASCADE
);

-- BANCA
CREATE TABLE Banca (
    id_banca SERIAL PRIMARY KEY,  
    nome VARCHAR(100) NOT NULL UNIQUE
);

-- ATM
CREATE TABLE Atm (
    id_atm SERIAL PRIMARY KEY,  
    id_citta INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    indirizzo TEXT,
    id_banca INT NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    FOREIGN KEY (id_citta) REFERENCES Citta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_banca) REFERENCES Banca(id) ON DELETE CASCADE
);

-- Tabella Utente
CREATE TABLE Utente (
    id_utente SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    data_registrazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Profilo
CREATE TABLE Profilo (
    id_profilo SERIAL PRIMARY KEY,
    id_utente INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100),
    codice_fiscale VARCHAR(16) UNIQUE,
    telefono VARCHAR(20),
    id_citta INT NOT NULL,
    indirizzo VARCHAR(255),
    data_nascita DATE NOT NULL,
    FOREIGN KEY (id_utente) REFERENCES Utente(id_utente) ON DELETE CASCADE,
    FOREIGN KEY (id_citta) REFERENCES Cap(id_citta) ON DELETE CASCADE
);

-- Tabella Conto
CREATE TABLE Conto (
    id_conto SERIAL PRIMARY KEY,
    id_profilo INT NOT NULL,
    saldo DECIMAL(15, 2) NOT NULL DEFAULT 0,
    numero_rapporto VARCHAR(12) NOT NULL UNIQUE,
    iban VARCHAR(34) NOT NULL UNIQUE,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_chiusura TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (id_profilo) REFERENCES Profilo(id_profilo) ON DELETE CASCADE
);

-- Tabella Circuito
CREATE TABLE Circuito (
    id_circuito SERIAL PRIMARY KEY,  
    nome VARCHAR(100) NOT NULL UNIQUE,
    descrizione VARCHAR(100),
    url_immagine VARCHAR(200)
);

-- Tabella Carta
CREATE TABLE Carta (
    id_carta SERIAL PRIMARY KEY,
    id_conto INT,
    numero VARCHAR(16) NOT NULL UNIQUE,
    data_scadenza DATE NOT NULL,
    cvv CHAR(3) NOT NULL,
    id_circuito INT NOT NULL,
    data_inizio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fine TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (id_conto) REFERENCES Conto(id_conto) ON DELETE CASCADE,
    FOREIGN KEY (id_circuito) REFERENCES Circuito(id_circuito) ON DELETE CASCADE
);

-- Tabella Operazione
CREATE TABLE Operazione (
    id_operazione SERIAL PRIMARY KEY,
    id_conto INT NOT NULL,
    cod_tipo INTEGER NOT NULL,
    importo DECIMAL(15, 2) NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_conto) REFERENCES Conto(id_conto) ON DELETE CASCADE
);

-- Tabella Bonifico
CREATE TABLE Bonifico (
    id_operazione INT PRIMARY KEY,
    cod_tipo INTEGER NOT NULL,
    iban VARCHAR(34),
    nome VARCHAR(100) NOT NULL,
    causale VARCHAR(255),
    istantaneo BOOLEAN DEFAULT FALSE,
    data_accredito TIMESTAMP,
    data_addebito TIMESTAMP,
    cod_stato INTEGER NOT NULL,
    FOREIGN KEY (id_operazione) REFERENCES Operazione(id_operazione) ON DELETE CASCADE
);

-- Tabella Pagamento
CREATE TABLE Pagamento (
    id_operazione INT PRIMARY KEY,
    esercente VARCHAR(100) NOT NULL,
    id_carta INT NOT NULL,
    FOREIGN KEY (id_operazione) REFERENCES Operazione(id_operazione) ON DELETE CASCADE,
    FOREIGN KEY (id_carta) REFERENCES Carta(id_carta) ON DELETE CASCADE
);

-- Tabella OpSportello
CREATE TABLE OpSportello (
    id_operazione INT PRIMARY KEY,
    id_atm INT NOT NULL,
    FOREIGN KEY (id_atm) REFERENCES Atm(id_atm) ON DELETE CASCADE
);
