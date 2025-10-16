🎮 Relazione di Progetto – SonicX
Analisi
Requisiti

Il progetto SonicX mira alla realizzazione di un videogioco a piattaforme bidimensionale ispirato alla saga di Sonic the Hedgehog.
L’obiettivo principale è unire la programmazione orientata agli oggetti (OOP) con la progettazione di un motore di gioco 2D, permettendo di mettere in pratica concetti di ereditarietà, polimorfismo, incapsulamento e gestione degli eventi.

Requisiti funzionali

Movimento del personaggio principale (corsa, salto, accelerazione, interazione con molle e piattaforme mobili).

Gestione delle collisioni con nemici, ostacoli e piattaforme.

Implementazione di più livelli con obiettivi e boss finale.

Sistema HUD con punteggio, tempo e vite.

Menu iniziale e schermate di vittoria/sconfitta.

Gestione di musica di sottofondo ed effetti sonori.

Requisiti non funzionali

Portabilità su Java 11+.

Architettura modulare, leggibile e facilmente estendibile.

Gestione fluida degli input e rendering grafico stabile tramite JavaFX.

Esecuzione ottimizzata per PC standard.

Analisi e modello del dominio

Il dominio del gioco è composto da un insieme di entità principali che cooperano per formare l’ambiente di gioco:

Entità	Descrizione
Player	Il personaggio controllato dall’utente (Sonic). Gestisce movimento, fisica e animazioni.
Enemy / Boss	Nemici autonomi o con comportamento di attacco, compreso un boss finale.
Level	Contenitore di piattaforme, molle, nemici e obiettivi del livello.
CollisionManager	Gestisce tutte le collisioni tra entità e superfici.
Spring / MovingPlatform / FinishGate	Elementi dinamici che influenzano il movimento o segnano la fine del livello.
SoundManager	Centralizza la gestione di suoni ed effetti.
HUD	Mostra vite, punteggio e tempo di gioco.
Schema UML del dominio
classDiagram
    class GameApp {
        +start()
        +initGame()
    }

    class Player {
        -x : double
        -y : double
        -velocityX : double
        -velocityY : double
        -isJumping : boolean
        +update()
        +jump()
        +moveLeft()
        +moveRight()
        +render()
    }

    class Enemy {
        -x : double
        -y : double
        +update()
        +render()
    }

    class Boss {
        -state : BossState
        +update()
        +attack()
        +changeState()
    }

    class Level {
        -platforms : List
        -enemies : List
        -player : Player
        +update()
        +render()
    }

    class CollisionManager {
        +checkCollision(entityA, entityB)
        +resolveCollisions()
    }

    class SoundManager {
        +playSound()
        +stopAll()
    }

    class HUD {
        -score : int
        -time : int
        -lives : int
        +update()
        +render()
    }

    GameApp --> Level
    Level --> Player
    Level --> Enemy
    Level --> Boss
    Level --> CollisionManager
    Player --> SoundManager
    Level --> HUD

Design
Architettura

L’architettura segue lo schema Model-View-Controller (MVC) semplificato:

Componente	Responsabilità
Model	Gestione logica di gioco (entità, collisioni, livelli).
View	Rendering grafico e HUD (JavaFX).
Controller	Gestione degli input e del ciclo di gioco (GameApp, InputHandler).

Il game loop, implementato tramite AnimationTimer, segue tre fasi principali:

Input: lettura dei tasti premuti e rilasciati.

Update: aggiornamento di logica, posizione, fisica e collisioni.

Render: disegno degli elementi grafici a schermo.

Questa struttura garantisce fluidità e sincronizzazione costante.

Design dettagliato
Pattern software utilizzati
Pattern	Ruolo nel progetto
State / Strategy	Gestione degli stati del Player (camminata, salto, invulnerabilità, scudo).
Observer	L’HUD osserva il Player e aggiorna punteggio, vite e tempo.
Template Method	Le classi Level1, Level2, Level3 derivano da Level, modificando solo layout e nemici.
Singleton	SoundManager garantisce un’unica istanza per la gestione audio.
Factory	Gestione modulare della creazione di entità e livelli.
UML dei principali pattern
classDiagram
    class PlayerState {
        <<interface>>
        +handleInput()
        +update()
    }

    class WalkingState
    class JumpingState
    class InvulnerableState

    PlayerState <|.. WalkingState
    PlayerState <|.. JumpingState
    PlayerState <|.. InvulnerableState
    Player --> PlayerState


Il pattern State permette di separare i diversi comportamenti di Sonic (camminata, salto, invulnerabilità), favorendo l’estendibilità e la chiarezza del codice.

Sviluppo
Testing automatizzato

È stato implementato un sistema di test automatico con JUnit.
Le principali classi testate sono:

CollisionManager: verifica delle collisioni corrette e gestione delle interazioni.

Player: corretto comportamento in salto, danno e movimento.

SoundManager: caricamento e riproduzione degli effetti.

HUD: aggiornamento coerente del punteggio e delle vite.

Note di sviluppo

Durante la fase di sviluppo sono stati adottati alcuni accorgimenti per ottimizzare il codice:

Uso di enum Axis per la direzione di movimento delle piattaforme mobili.

Metodo bounceOnSpring() nel Player per simulare la fisica della molla.

Gestione delle animazioni tramite Timer JavaFX.

Utilizzo di RandomUtil per generare casualmente nemici e oggetti bonus.

Implementazione asincrona di audio e musica per ridurre la latenza.

Separazione completa tra logica di gioco e rendering grafico.

Commenti finali
Autovalutazione e lavori futuri

Mi sono occupato personalmente della progettazione e dello sviluppo del progetto SonicX in tutte le sue fasi:

Struttura architetturale e organizzazione dei pacchetti.

Gestione collisioni, movimento e fisica di gioco.

Progettazione dei livelli e dell’HUD.

Integrazione dell’audio e del menu grafico.

Punti di forza

Architettura pulita e modulare.

Codice riutilizzabile e facilmente estendibile.

Gameplay fluido e coerente con la logica classica dei platform 2D.

Punti da migliorare

Ottimizzazione del caricamento iniziale e delle texture.

Maggiore copertura dei test unitari.

Migliore separazione tra logica e rendering in alcune classi.

Sviluppi futuri

Introduzione di power-up (scudi, velocità, invincibilità).

Sistema di punteggio globale e salvataggio progressi.

Miglioramento della fisica (pendenze, attrito, molle avanzate).

Aggiunta di nuovi livelli e modalità bonus.

Difficoltà riscontrate

Le principali difficoltà emerse durante lo sviluppo sono state:

Gestione corretta delle collisioni tra entità multiple in movimento.

Sincronizzazione tra logica di gioco e thread grafico JavaFX.

Integrazione fluida di musica ed effetti senza bloccare il rendering.

Ottimizzazione delle prestazioni per mantenere un frame rate costante.

Guida utente
Avvio del gioco

Per avviare il gioco, eseguire la classe principale:

java GameApp


Oppure da IDE:

Eseguire game.Main o GameApp.java con JavaFX configurato.

Comandi
Tasto	Azione
← / →	Muove Sonic a sinistra o destra
Spazio	Salta
Invio (Enter)	Avvia la partita dal menu
Esc	Torna al menu principale
Obiettivo

Raggiungere la FinishGate di ogni livello evitando nemici e raccogliendo anelli.
La partita termina in caso di perdita di tutte le vite o dopo la sconfitta del boss finale.

Conclusioni

Il progetto SonicX rappresenta un caso pratico di come le tecniche di programmazione a oggetti possano essere applicate in un contesto videoludico.
L’architettura modulare, la gestione del ciclo di gioco e l’integrazione con JavaFX dimostrano la capacità di coniugare teoria e pratica in un risultato concreto, interattivo e coerente con le logiche del game design moderno.
