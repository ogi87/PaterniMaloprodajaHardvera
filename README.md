# Projekat: Maloprodajni podsistem računarske opreme (Adapter Pattern)

Ovaj projekat implementira modularni softverski sistem za praćenje poslovanja prodavnice računarske opreme u **Java Swing** okruženju, razvijen u okviru predmeta **Softverski Paterni / Projektovanje softvera**.

Arhitektura sistema oslanja se na strukturni projektni uzor **Adapter (Adapter Pattern)** koji omogućava uniformno uključivanje i prikaz servisnih usluga (instalacija, održavanje) unutar postojećeg maloprodajnog podsistema koji je izvorno projektovan isključivo za rad sa hardverskim komponentama — **bez ikakvih izmena u postojećem kodu maloprodajnog podsistema**.

---

## 1. Motivacija i problem

U sistemu postoje dva nezavisno razvijena podsistema:
1. **Maloprodajni objekti (Retail)**: Prodaju hardverske komponente. Sve komponente su predstavljene interfejsom `Komponenta` (`dajProdajnuCijenu()`, `dajSpecifikaciju()`, `dajDobavljaca()`). Maloprodaja sadrži funkcionalnost generisanja izveštaja o svim prodatim artiklima sa njihovim specifikacijama i cenama.
2. **Servisna služba (Service)**: Pruža usluge sklapanja, instalacije i servisiranja računara. Sve usluge su predstavljene interfejsom `Usluga` (`dajCijenuBezPDVa()`, `dajPDV()`, `dajOpisAktivnosti()`, `dajServisera()`).

**Problem:** Kako omogućiti da se u izveštaju maloprodaje ravnopravno nađu i usluge instalacije, sa uračunatim porezom, opisom aktivnosti i serviserom, a da se pritom ne menja postojeći kod podsistema za maloprodaju niti interfejs `Komponenta`?

**Rešenje:** Primena objektnog **Adapter projektnog uzora**.

---

## 2. Primenjeni projektni uzor: Adapter (Objektni adapter)

### 2.1. Uloge u uzoru:
- **Ciljni interfejs (Target):** `komponenta.Komponenta`
  - Interfejs koji klijentski podsistem poznaje i očekuje.
- **Prilagođavani interfejs (Adaptee):** `usluga.Usluga`
  - Postojeći interfejs servisne službe čije metode nisu direktno kompatibilne sa `Komponenta`.
- **Adapter (Adapter):** `adapter.UslugaAdapter`
  - Implementira `Komponenta`, a kroz kompoziciju drži referencu na instancu `Usluga`.
  - Preslikava pozive metoda sa interfejsa `Komponenta` na odgovarajuće metode i kalkulacije interfejsa `Usluga`.
- **Klijent (Client):** `podsistem.MaloprodajniPodsistem` i `form.MainForm`
  - Radi isključivo sa interfejsom `Komponenta`.

### 2.2. Tabela preslikavanja metoda u adapteru (`UslugaAdapter`):

| Metoda ciljnog interfejsa (`Komponenta`) | Odgovarajuća implementacija / kalkulacija iz `Usluga` | Objašnjenje |
| :--- | :--- | :--- |
| **`dajProdajnuCijenu()`** | `cenaBezPDV * (1.0 + stopaPDV)` | Usluga ima cenu bez poreza i procenat PDV-a; adapter izračunava konačnu maloprodajnu cenu sa porezom. |
| **`dajSpecifikaciju()`** | `"[USLUGA INSTALACIJE] " + usluga.dajOpisAktivnosti()` | Opis servisnih radova se prikazuje na mestu gde maloprodajni izveštaj očekuje specifikaciju. |
| **`dajDobavljaca()`** | `"Serviser: " + usluga.dajServisera()` | Serviser koji izvršava radove se preslikava u dobavljača/izvršioca stavke. |

### 2.3. Dijagram klasa (UML / Mermaid):

```mermaid
classDiagram
    direction TB
    
    class Komponenta {
        <<interface>>
        +dajProdajnuCijenu() double
        +dajSpecifikaciju() String
        +dajDobavljaca() String
    }

    class HardverskaKomponenta {
        -String naziv
        -String specifikacija
        -String dobavljac
        -double prodajnaCijena
        +dajProdajnuCijenu() double
        +dajSpecifikaciju() String
        +dajDobavljaca() String
    }

    class Usluga {
        <<interface>>
        +dajCijenuBezPDVa() double
        +dajPDV() double
        +dajOpisAktivnosti() String
        +dajServisera() String
    }

    class InstalacionaUsluga {
        -String naziv
        -String opisAktivnosti
        -String serviser
        -double cijenaBezPDVa
        -double stopaPDVa
        +dajCijenuBezPDVa() double
        +dajPDV() double
        +dajOpisAktivnosti() String
        +dajServisera() String
    }

    class UslugaAdapter {
        -Usluga usluga
        +UslugaAdapter(Usluga usluga)
        +dajProdajnuCijenu() double
        +dajSpecifikaciju() String
        +dajDobavljaca() String
    }

    class MaloprodajniPodsistem {
        -String nazivObjekta
        -List~Komponenta~ prodateStavke
        +dodajProdatuKomponentu(Komponenta k)
        +ukloniStavku(int index)
        +izracunajUkupnuProdajnuCijenu() double
        +generisiIzvjestaj() String
    }

    Komponenta <|.. HardverskaKomponenta : implementira
    Komponenta <|.. UslugaAdapter : implementira (Target)
    Usluga <|.. InstalacionaUsluga : implementira
    UslugaAdapter o--> Usluga : kompozicija (Adaptee)
    MaloprodajniPodsistem o--> Komponenta : operiše isključivo sa
```

---

## 3. Struktura projekta

```
DrugiProjekatPaterni/
├── nbproject/                          <-- NetBeans projektna konfiguracija (otvaranje i F6)
│   ├── project.xml
│   ├── project.properties
│   └── build-impl.xml
├── src/
│   ├── komponenta/
│   │   ├── Komponenta.java             <-- Target interfejs maloprodaje
│   │   └── HardverskaKomponenta.java   <-- Konkretna hardverska stavka
│   ├── usluga/
│   │   ├── Usluga.java                 <-- Adaptee interfejs servisne službe
│   │   └── InstalacionaUsluga.java     <-- Konkretna instalaciona usluga
│   ├── adapter/
│   │   └── UslugaAdapter.java          <-- Adapter koji prilagođava Usluga na Komponenta
│   ├── podsistem/
│   │   └── MaloprodajniPodsistem.java  <-- Maloprodajni podsistem (evidencija i izveštaj)
│   ├── form/
│   │   └── MainForm.java / .form       <-- NetBeans JFrame Form (čist minimalistički GUI Designer)
│   └── main/
│       └── Main.java                   <-- Glavna ulazna tačka programa
├── test/
│   └── adapter/
│       └── AdapterTest.java            <-- Automatski test kalkulacije i integracije
├── dist/
│   └── DrugiProjekatPaterni.jar        <-- Gotov kompajliran JAR arhiv
├── build.xml                           <-- Ant skripta
├── manifest.mf
└── README.md
```

---

## 4. Korisnički interfejs (Swing GUI)

Forma `MainForm` je dizajnirana minimalistički i pregledno, u potpunosti kompatibilna sa **NetBeans GUI Builder (Design mod)**:

1. **Panel „Unos hardverske komponente“:**
   - Polja: *Naziv*, *Dobavljač*, *Prodajna cena (RSD)*.
   - Klikom na dugme **„Dodaj komponentu“** instancira se `HardverskaKomponenta` i dodaje u podsistem:
     ```java
     Komponenta k = new HardverskaKomponenta(naziv, dobavljac, cena);
     podsistem.dodajProdatuKomponentu(k);
     ```

2. **Panel „Unos servisne usluge (Adapter)“:**
   - Polja: *Opis usluge*, *Serviser*, *Cena bez PDV*, *PDV (%)* (podrazumevano 20%).
   - Klikom na dugme **„Dodaj uslugu (Adapter)“** demonstrira se srž Adapter uzora:
     ```java
     // 1. Kreiranje Adaptee objekta
     Usluga usluga = new InstalacionaUsluga(opis, serviser, cenaBezPdv, pdv);

     // 2. Obavijanje u Adapter (koji implementira Komponenta)
     Komponenta adapter = new UslugaAdapter(usluga);

     // 3. Maloprodaja prihvata adapter jer očekuje Komponenta!
     podsistem.dodajProdatuKomponentu(adapter);
     ```

3. **Tabela prodatih stavki (`tblStavke`):**
   - Kolone: *R.br.*, *Tip*, *Specifikacija / Opis*, *Dobavljač / Serviser*, *Cena sa PDV (RSD)*.
   - Polimorfno prikazuje i hardver i adaptirane usluge.

4. **Donje komande i akcije:**
   - **Labela ukupnog iznosa:** Prikazuje zbirnu vrednost svih stavki u maloprodaji.
   - **Dugme „Učitaj demo“:** Popunjava tabelu primerima hardvera i usluga radi brze demonstracije na času.
   - **Dugme „Obriši stavku“:** Uklanja selektovani red iz evidencije maloprodaje.
   - **Dugme „Prikaži izveštaj“:** Otvara modalni prozor sa formatiranim tekstualnim izveštajem iz podsistema (`podsistem.kreirajIzvjestaj()`).

---

## 5. Pokretanje projekta

### Zahtevi:
- **Java SE Development Kit (JDK):** verzija 17 ili novija (podržava i najnoviji JDK 25)
- **NetBeans IDE** (ili IntelliJ IDEA / Eclipse)

### Pokretanje iz NetBeans IDE:
1. Otvorite NetBeans i izaberite **File** -> **Open Project**.
2. Odaberite folder `DrugiProjekatPaterni`.
3. Pritisnite **F6** (ili zelenu ikonicu **Run Project**).

### Pokretanje iz komandne linije (JAR):
U folderu `DrugiProjekatPaterni` pokrenite:
```bash
java -jar dist/DrugiProjekatPaterni.jar
```

### Pokretanje automatskog testa:
```bash
java -cp "build/classes;build/test/classes" adapter.AdapterTest
```
