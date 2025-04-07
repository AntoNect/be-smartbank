<h1>BackEnd del Project Work UniPegaso - De Filippis Antonio Pio - Matr: 0312300708 </h1>

E' possibile utilizzare il seguente account demo, o crearne di nuovi. <br>
<b>Username:</b> DemoPegaso <br>
<b>Password:</b> Pegaso1234 <br>

Di seguito gli script utilizzati per la creazione delle tabelle: <br>

-- Tabella Stato <br>
CREATE TABLE Stato ( <br>
    id_stato SERIAL PRIMARY KEY,  <br>
    nome VARCHAR(100) NOT NULL, <br>
    sigla CHAR(2) NOT NULL <br>
); <br>
 <br>
-- Tabella Regione <br>
CREATE TABLE Regione ( <br>
    id_regione SERIAL PRIMARY KEY, <br>
    nome VARCHAR(100) NOT NULL, <br>
    id_stato INT NOT NULL, <br>
    FOREIGN KEY (id_stato) REFERENCES Stato(id) ON DELETE CASCADE <br>
); <br>
 <br>
-- Tabella Provincia <br>
CREATE TABLE Provincia ( <br>
    id_provincia SERIAL PRIMARY KEY, <br>
    sigla CHAR(2) NOT NULL UNIQUE, <br>
    id_regione INT NOT NULL, <br>
    FOREIGN KEY (id_regione) REFERENCES Regione(id) ON DELETE CASCADE <br>
); <br>
 <br>
-- Tabella Città <br>
CREATE TABLE Citta ( <br>
    id_citta SERIAL PRIMARY KEY, <br>
    nome VARCHAR(100) NOT NULL, <br>
    id_provincia INT NOT NULL, <br>
    FOREIGN KEY (id_provincia) REFERENCES Provincia(id) ON DELETE CASCADE <br>
); <br>
 <br>
-- BANCA <br>
CREATE TABLE Banca ( <br>
    id_banca SERIAL PRIMARY KEY,   <br>
    nome VARCHAR(100) NOT NULL UNIQUE <br>
); <br>
 <br>
-- ATM <br>
CREATE TABLE Atm ( <br>
    id_atm SERIAL PRIMARY KEY,   <br>
    id_citta INT NOT NULL, <br>
    nome VARCHAR(100) NOT NULL, <br>
    indirizzo TEXT, <br>
    id_banca INT NOT NULL, <br>
    latitude DOUBLE PRECISION, <br>
    longitude DOUBLE PRECISION, <br>
    FOREIGN KEY (id_citta) REFERENCES Citta(id) ON DELETE CASCADE, <br>
    FOREIGN KEY (id_banca) REFERENCES Banca(id) ON DELETE CASCADE <br>
); <br>

-- Tabella Utente <br>
CREATE TABLE Utente ( <br>
    id_utente SERIAL PRIMARY KEY, <br>
    email VARCHAR(100) NOT NULL UNIQUE, <br>
    username VARCHAR(50) NOT NULL UNIQUE, <br>
    password VARCHAR(255) NOT NULL, <br>
    data_registrazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP <br>
); <br>
 <br>
-- Tabella Profilo <br>
CREATE TABLE Profilo (
    id_profilo SERIAL PRIMARY KEY, <br>
    id_utente INT NOT NULL, <br>
    nome VARCHAR(100) NOT NULL, <br>
    cognome VARCHAR(100), <br>
    codice_fiscale VARCHAR(16) UNIQUE, <br>
    telefono VARCHAR(20), <br>
    id_citta INT NOT NULL, <br>
    indirizzo VARCHAR(255), <br>
    data_nascita DATE NOT NULL, <br>
    FOREIGN KEY (id_utente) REFERENCES Utente(id_utente) ON DELETE CASCADE, <br>
    FOREIGN KEY (id_citta) REFERENCES Cap(id_citta) ON DELETE CASCADE <br>
); <br>
 <br>
-- Tabella Conto <br>
CREATE TABLE Conto ( <br>
    id_conto SERIAL PRIMARY KEY, <br>
    id_profilo INT NOT NULL, <br>
    saldo DECIMAL(15, 2) NOT NULL DEFAULT 0, <br>
    numero_rapporto VARCHAR(12) NOT NULL UNIQUE, <br>
    iban VARCHAR(34) NOT NULL UNIQUE, <br>
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP, <br>
    data_chiusura TIMESTAMP DEFAULT NULL, <br>
    FOREIGN KEY (id_profilo) REFERENCES Profilo(id_profilo) ON DELETE CASCADE <br>
); <br>
 <br>
-- Tabella Circuito <br>
CREATE TABLE Circuito ( <br>
    id_circuito SERIAL PRIMARY KEY,   <br>
    nome VARCHAR(100) NOT NULL UNIQUE, <br>
    descrizione VARCHAR(100), <br>
    url_immagine VARCHAR(200) <br>
); <br>
 <br>
-- Tabella Carta <br>
CREATE TABLE Carta ( <br>
    id_carta SERIAL PRIMARY KEY, <br>
    id_conto INT, <br>
    numero VARCHAR(16) NOT NULL UNIQUE, <br>
    data_scadenza DATE NOT NULL, <br>
    cvv CHAR(3) NOT NULL, <br>
    id_circuito INT NOT NULL, <br>
    data_inizio TIMESTAMP DEFAULT CURRENT_TIMESTAMP, <br>
    data_fine TIMESTAMP DEFAULT NULL, <br>
    FOREIGN KEY (id_conto) REFERENCES Conto(id_conto) ON DELETE CASCADE, <br>
    FOREIGN KEY (id_circuito) REFERENCES Circuito(id_circuito) ON DELETE CASCADE <br>
); <br>
 <br>
-- Tabella Operazione <br>
CREATE TABLE Operazione ( <br>
    id_operazione SERIAL PRIMARY KEY, <br>
    id_conto INT NOT NULL, <br>
    cod_tipo INTEGER NOT NULL, <br>
    importo DECIMAL(15, 2) NOT NULL, <br>
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP, <br>
    FOREIGN KEY (id_conto) REFERENCES Conto(id_conto) ON DELETE CASCADE <br>
); <br>
 <br>
-- Tabella Bonifico <br>
CREATE TABLE Bonifico ( <br>
    id_operazione INT PRIMARY KEY, <br>
    cod_tipo INTEGER NOT NULL, <br>
    iban VARCHAR(34), <br>
    nome VARCHAR(100) NOT NULL, <br>
    causale VARCHAR(255), <br>
    istantaneo BOOLEAN DEFAULT FALSE, <br>
    data_accredito TIMESTAMP, <br>
    data_addebito TIMESTAMP, <br>
    cod_stato INTEGER NOT NULL, <br>
    FOREIGN KEY (id_operazione) REFERENCES Operazione(id_operazione) ON DELETE CASCADE <br>
); <br>
 <br>
-- Tabella Pagamento <br>
CREATE TABLE Pagamento ( <br>
    id_operazione INT PRIMARY KEY, <br>
    esercente VARCHAR(100) NOT NULL, <br>
    id_carta INT NOT NULL, <br>
    FOREIGN KEY (id_operazione) REFERENCES Operazione(id_operazione) ON DELETE CASCADE, <br>
    FOREIGN KEY (id_carta) REFERENCES Carta(id_carta) ON DELETE CASCADE <br>
); <br>
 <br>
-- Tabella OpSportello <br>
CREATE TABLE OpSportello ( <br>
    id_operazione INT PRIMARY KEY, <br>
    id_atm INT NOT NULL, <br>
    FOREIGN KEY (id_atm) REFERENCES Atm(id_atm) ON DELETE CASCADE <br>
); <br>
 <br>
