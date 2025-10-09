Relazione progetto – Gioco stile SonicX
1. Abstract

Ho realizzato questo progetto interamente da solo con l’obiettivo di sviluppare un videogioco 2D in Java ispirato a Sonic the Hedgehog.
La mia idea era quella di costruire un piccolo motore di gioco completamente funzionante, implementando tutte le componenti principali: il movimento del personaggio (Sonic), la presenza di nemici con comportamenti diversi, la gestione di più livelli con ostacoli e piattaforme, il controllo delle collisioni, un HUD con punteggio e tempo, e un menu grafico interattivo.
Questo progetto mi ha permesso di mettere in pratica concretamente i concetti di programmazione orientata agli oggetti, progettazione UML e design pattern appresi durante il corso.

2. Analisi
2.1 Requisiti

Requisiti funzionali:

Implementare il movimento del personaggio principale (corsa, salto, interazione con molle e piattaforme).

Gestire nemici con logiche e pattern di comportamento diversi.

Realizzare più livelli, ognuno con obiettivi e ostacoli distinti.

Inserire un HUD con punteggio, tempo e vite residue.

Aggiungere un menu iniziale e un’interfaccia grafica intuitiva.

Integrare musica ed effetti sonori per un’esperienza più immersiva.

Requisiti non funzionali:

Portabilità del gioco su Java 8 e versioni successive.

Struttura modulare a pacchetti, per favorire estendibilità e riutilizzo del codice.

Codice chiaro, commentato e leggibile.

Buone prestazioni anche su PC di fascia media.

2.2 Analisi e modello del dominio

Durante la fase di analisi, ho definito le principali entità del gioco:
Player, Enemy, Projectile, Shield, Level, CollisionManager, Spring, MovingPlatform e FinishGate.
Il giocatore interagisce con l’ambiente e con i nemici, mentre il CollisionManager gestisce tutte le intersezioni tra oggetti, determinando le reazioni fisiche o logiche.
Ho rappresentato queste entità e le loro relazioni attraverso uno schema UML per garantire coerenza strutturale e chiarezza nella progettazione.

3. Design
3.1 Architettura

L’architettura che ho scelto segue un approccio Model-View-Controller (MVC) semplificato:

Model: contiene la logica di gioco e le entità principali (Player, Enemy, Projectile, Shield, Level, CollisionManager).

View: gestisce il rendering e l’interfaccia utente, tramite classi come HUD e MenuSonicGrafico.

Controller: include la classe principale GameApp e il gestore di input InputHandler, che coordina gli eventi da tastiera.

Questa separazione ha reso il progetto più ordinato e facile da mantenere.

3.2 Design dettagliato

Ho applicato diversi design pattern per migliorare la struttura:

Strategy: per gestire gli stati del Player (camminare, saltare, invulnerabilità, ecc.) come strategie intercambiabili.

Observer: per aggiornare dinamicamente l’HUD in base ai cambiamenti di stato del giocatore (vite, punteggio, tempo).

Template Method: per i livelli (Level1, Level2, Level3) che ereditano la logica base da Level, modificando solo il layout o i nemici.

4. Sviluppo
4.1 Testing automatizzato

Ho realizzato test JUnit di base per verificare il corretto funzionamento delle collisioni e della gestione delle vite del giocatore.
I test su CollisionManager e Player hanno confermato la stabilità della logica di base del gioco.

4.2 Note di sviluppo

Durante lo sviluppo, ho introdotto diversi accorgimenti tecnici:

Uso dell’enum Axis per rappresentare il movimento delle piattaforme mobili (orizzontali o verticali).

Implementazione di un sistema di temporizzazione con Timer per gestire animazioni e invulnerabilità temporanee.

Creazione del metodo bounceOnSpring(Spring s) nel Player, per simulare la fisica della molla.

Gestione centralizzata delle collisioni in CollisionManager, con dispatch basato sul tipo di entità coinvolta.

Introduzione di una classe RandomUtil per variare casualmente nemici e oggetti, rendendo ogni partita meno prevedibile.

5. Commenti finali
5.1 Autovalutazione e sviluppi futuri

Durante lo sviluppo di SonicX ho curato tutti gli aspetti del progetto, dal design delle entità alla gestione delle collisioni, dal sistema HUD alla logica dei livelli e del menu.

Punti di forza: codice modulare e riutilizzabile, architettura chiara, gameplay fluido.

Da migliorare: documentazione interna e alcuni aspetti della separazione tra logica e grafica.
Per il futuro, mi piacerebbe aggiungere:

un sistema di salvataggio dei progressi;

più tipi di nemici e power-up;

una fisica più realistica e miglioramenti nell’audio asincrono.

5.2 Difficoltà incontrate

Le principali difficoltà che ho affrontato sono state:

la sincronizzazione tra la logica del gioco e il thread grafico di JavaFX;

la gestione accurata delle collisioni con molte entità contemporaneamente;

l’integrazione della fisica con il rendering, in modo da mantenere fluidità e coerenza visiva.

6. Guida utente

Per avviare il gioco è sufficiente eseguire la classe game.Main.
Comandi:

Frecce direzionali: muovere Sonic.

Spazio: salto.

Invio: avvio partita dal menu principale.

Obiettivo del gioco: raggiungere il FinishGate alla fine di ogni livello evitando i nemici e raccogliendo gli oggetti bonus.
