Startup Idea Tracker
O aplicație Android modernă, construită în Kotlin și Jetpack Compose. Proiectul servește ca un jurnal digital simplu și eficient pentru notarea și gestionarea rapidă a ideilor de aplicații, proiecte personale sau afaceri.

Funcționalități Principale
Aplicația este formată din două ecrane complet funcționale, interconectate printr-un sistem de navigare fluid:

Ecranul Principal (Dashboard-ul): Afișează toate ideile salvate folosind o listă scrollabilă și eficientă din punct de vedere al memoriei. Gestionează starea de listă goală, afișând un mesaj de ghidare pentru utilizator.

Ecranul de Adăugare: Un formular curat care preia datele introduse de utilizator (Titlu și Descriere). Salvează datele și returnează automat utilizatorul la ecranul principal, actualizând interfața instantaneu.

Arhitectură și Tehnologii Folosite
Aplicația renunță complet la abordarea clasică bazată pe fișiere XML și adoptă o paradigmă UI Declarativă, implementând o arhitectură curată de tip MVVM (Model-View-ViewModel).

Kotlin: Limbajul de bază al aplicației, utilizând concepte precum Data Classes pentru modelarea datelor.

Jetpack Compose: Utilizat pentru construirea întregii interfețe grafice (componente precum Scaffold, LazyColumn, TopAppBar, Material3).

ViewModel: Gestionează logica aplicației și protejează datele împotriva distrugerii la evenimente precum rotirea ecranului sau recompunerea interfeței.

StateFlow și Coroutines: Implementate în interiorul ViewModel-ului pentru a expune starea datelor către interfața grafică într-un mod reactiv (Single Source of Truth). La modificarea datelor, Compose redesenează doar elementele vizate de schimbare.

Navigation Compose: Gestionează rutele (NavHost, NavController) și tranziția stării între ecrane, renunțând la utilizarea multiplă de Activități sau Fragmente clasice.

Cum se rulează proiectul
Clonează acest repository pe mașina locală.

Deschide proiectul folosind Android Studio.

Așteaptă finalizarea procesului Gradle Sync pentru descărcarea dependențelor necesare (inclusiv androidx.navigation:navigation-compose).

Rulează aplicația pe un emulator (recomandat API 24+) sau pe un dispozitiv fizic conectat.

<img width="1108" height="672" alt="image" src="https://github.com/user-attachments/assets/95c7a1ae-5045-4e64-82fe-2993ead90dc9" />
<img width="953" height="607" alt="image" src="https://github.com/user-attachments/assets/24a07f5c-1591-4096-ac9b-008a9ec10b25" />
