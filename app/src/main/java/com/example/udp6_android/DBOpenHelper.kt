package com.example.udp6_android

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.udp6_android.contracts.CinemaContract
import com.example.udp6_android.contracts.MovieContract
import com.example.udp6_android.model.Cinema
import com.example.udp6_android.model.Movie
import com.example.udp6_android.model.City

class DBOpenHelper private constructor(context: Context?):
    SQLiteOpenHelper(context, MovieContract.DB_NAME, null, MovieContract.VERSION)
{
    companion object {
        private var dbOpen: DBOpenHelper? = null
        fun getInstance(context: Context?): DBOpenHelper? {
            if(dbOpen == null) dbOpen = DBOpenHelper(context)
            return dbOpen
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(
                "CREATE TABLE ${MovieContract.Companion.Entry.TABLE}(" +
                        "${MovieContract.Companion.Entry.ID} INTEGER PRIMARY KEY, " +
                        "${MovieContract.Companion.Entry.TITLE} TEXT, " +
                        "${MovieContract.Companion.Entry.DESCRIPTION} TEXT, " +
                        "${MovieContract.Companion.Entry.IMG_ID} INTEGER, " +
                        "${MovieContract.Companion.Entry.DURATION} INTEGER, " +
                        "${MovieContract.Companion.Entry.RELEASE_YEAR} INTEGER, " +
                        "${MovieContract.Companion.Entry.COUNTRY} TEXT);"
            )
            db.execSQL("CREATE TABLE ${CinemaContract.Entry.TABLE}(" +
                    "${CinemaContract.Entry.ID} INTEGER PRIMARY KEY, " +
                    "${CinemaContract.Entry.NAME} VARCHAR, " +
                    "${CinemaContract.Entry.CITY} VARCHAR, " +
                    "${CinemaContract.Entry.LATITUDE} REAL, " +
                    "${CinemaContract.Entry.LONGITUDE} REAL);"
            )
            db.execSQL("CREATE TABLE Movie_Cinema(movie_id INTEGER, cinema_id INTEGER);")
            initializeDb(db)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${MovieContract.Companion.Entry.TABLE};")
        onCreate(db)
    }

    private fun initializeDb(db: SQLiteDatabase) {
        val cinemas = loadCinemas()
        val movies = loadMovies()
        cinemas.forEach { insertCinema(db, it) }
        movies.forEach { insertMovie(db, it) }
        movieCinemaManualInserts(db)
    }

    private fun insertMovie(
        db: SQLiteDatabase,
        movie: Movie,
    ) {
        db.execSQL(
            "INSERT INTO ${MovieContract.Companion.Entry.TABLE} (" +
                    "${MovieContract.Companion.Entry.TITLE}, " +
                    "${MovieContract.Companion.Entry.DESCRIPTION}," +
                    "${MovieContract.Companion.Entry.IMG_ID}," +
                    "${MovieContract.Companion.Entry.DURATION}," +
                    "${MovieContract.Companion.Entry.RELEASE_YEAR}," +
                    "${MovieContract.Companion.Entry.COUNTRY})" +
                    "values ('${movie.title}','${movie.description}',${movie.imgResId},${movie.duration},${movie.releaseYear},'${movie.country}');"
        )
        Log.d("Database", "Inserted Movie: ${movie.title}")
    }

    private fun insertCinema(
        db: SQLiteDatabase,
        it: Cinema,
    ) {
        db.execSQL(
            "INSERT INTO ${CinemaContract.Entry.TABLE} (" +
                    "${CinemaContract.Entry.NAME}," +
                    "${CinemaContract.Entry.CITY}," +
                    "${CinemaContract.Entry.LATITUDE}," +
                    "${CinemaContract.Entry.LONGITUDE})" +
                    "values ('${it.name}','${it.city}',${it.latitude},${it.longitude});"
        )
        Log.d("DBOpenHelper", "Inserted Cinema: ${it.name}")
    }

    private fun loadMovies(): MutableList<Movie> {
        return mutableListOf(
            Movie(1,"La vida es bella", "Un padre judío-italiano que utiliza su imaginación y humor para proteger a su hijo de los horrores de un campo de concentración nazi, transformando la tragedia en un juego para mantener viva la esperanza.",
                R.drawable.la_vida_es_bella,116, 1997, "Italia"
            ),
            Movie(2,"El padrino", "El envejecido patriarca de una dinastía del crimen organizado transfiere el control de su imperio clandestino a su hijo reacio.",
                R.drawable.elpadrino,175, 1972, "Estados Unidos"
            ),
            Movie(3,"El caballero oscuro","Cuando la amenaza conocida como El Joker emerge de su pasado misterioso, causa estragos y caos en la gente de Gotham.",
                R.drawable.el_caballero_oscuro,152, 2008, "Estados Unidos"
            ),
            Movie(4,"Pulp Fiction","La vida de dos sicarios de la mafia, un boxeador, la esposa de un gánster y dos bandidos se entrelazan en cuatro historias de violencia y redención.",
                R.drawable.pulpfiction,153, 1994, "Estados Unidos"
            ),
            Movie(5,"El Señor de los Anillos","Gandalf y Aragorn lideran el mundo de los hombres contra el ejército de Sauron para distraerlo de Frodo y Sam mientras se acercan al Monte del Destino con el Anillo Único.",
                R.drawable.senhoranillos,201, 2003, "Nueva Zelanda"
            ),
            Movie(6,"Forrest Gump","Los presidios de Forrest Gump, un hombre con un coeficiente intelectual bajo, tienen lugar durante varios eventos históricos estadounidenses.",
                R.drawable.forrestgump,142, 1994, "Estados Unidos"
            ),
            Movie(7, "Origen","Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartición de sueños recibe la tarea inversa de plantar una idea en la mente de un director ejecutivo.",
                R.drawable.origen,148, 2010, "Estados Unidos"
            ),
            Movie(8,"El club de la lucha","Un oficinista insomne y un fabricante de jabón desmotivado forman un club de lucha clandestino que se convierte en algo mucho más grande.",
                R.drawable.clublucha,139, 1999, "Estados Unidos"
            ),
            Movie(9,"El Imperio Contraataca","Después de que los rebeldes sean brutalmente sobrepasados por el Imperio en el planeta helado Hoth, Luke Skywalker comienza su entrenamiento Jedi con Yoda, mientras sus amigos son perseguidos por Darth Vader.",
                R.drawable.imperiocontraataca,124, 1980, "Estados Unidos"
            ),
            Movie(10,"El bueno, el malo y el feo","Un cazarrecompensas se asocia con un hombre para encontrar una fortuna en oro enterrado en un cementerio remoto.",
                R.drawable.buenomalofeo,161, 1966, "Italia"
            ),
            Movie(11,"Matrix","Un hacker informático aprende de rebeldes misteriosos sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores.",
                R.drawable.matrix,131, 1999, "Estados Unidos"
            ),
            Movie(12,"Uno de los nuestros","La historia de Henry Hill y su vida en la mafia, abarcando su relación con su esposa Karen Hill y sus socios mafiosos Jimmy Conway y Tommy DeVito.",
                R.drawable.uno_de_los_nuestros,148, 1990, "Estados Unidos"
            ),
            Movie(13,"La lista de Schindler","En la Polonia ocupada por los alemanes durante la Segunda Guerra Mundial, Oskar Schindler se preocupa gradualmente por su fuerza laboral judía después de presenciar su persecución por parte de los nazis.",
                R.drawable.la_lista_de_schindler,195, 1993, "Estados Unidos"
            ),
            Movie(14,"Interestelar","Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento por asegurar la supervivencia de la humanidad.",
                R.drawable.interstellar,169, 2014, "Estados Unidos"
            ),
            Movie(15,"Parásitos","La codicia y la discriminación de clase amenazan la recién formada relación simbiótica entre la adinerada familia Park y el empobrecido clan Kim.",
                R.drawable.parasitos,132, 2019, "Corea del sur"
            ),
            Movie(16,"La milla verde","Las vidas de los guardias en el corredor de la muerte se ven afectadas por uno de sus reclusos: un hombre negro acusado de asesinato infantil y violación, pero que tiene un don misterioso.",
                R.drawable.la_milla_verde,180, 1999, "Estados Unidos"
            ),
            Movie(17,"Cadena perpetua","Dos hombres encarcelados establecen una fuerte amistad a lo largo de los años, encontrando consuelo y redención eventual a través de actos de decencia común.",
                R.drawable.cadena_perpetua,142, 1994, "Estados Unidos"
            ),
            Movie(18,"El pianista","Narra la historia real de Władysław Szpilman, un pianista judío-polaco que lucha por sobrevivir durante la ocupación nazi en la Segunda Guerra Mundial, utilizando su talento y resiliencia en medio del horror.",
                R.drawable.el_pianista,148, 2002, "Reino Unido"
            )
        )
    }

    private fun loadCinemas(): MutableList<Cinema> = mutableListOf(
        Cinema(1, "Cine Vialia", City.Malaga, 36.667715308, -4.502452023),
        Cinema(2, "Yelmo Plaza Mayor", City.Malaga, 36.6768, -4.50172),
        Cinema(3, "Cinesa Diagonal", City.Madrid, 36.6768, -4.50172),
        Cinema(4, "Capitol", City.Madrid, 40.42049093624055, -3.7065717490750165),
        Cinema(5, "Cinemes Verdi", City.Barcelona, 41.40689588224367, 2.1564225855899086),
        Cinema(6, "Cinemes Girona", City.Barcelona, 41.402260665343086, 2.165348976900318),
        Cinema(7, "Codex Cinema", City.Lugo, 43.024218990433816, -7.566038413369055),
        Cinema(8, "Yelmo As termas", City.Lugo, 43.03877476362686, -7.570244116967229),
        Cinema(9, "Cinema NOS", City.Portugal, 38.7718093054458, -9.160708892144276),
        Cinema(10, "Cinema NOS Palacio do Gelo", City.Portugal, 40.71263408423472, -7.88853821830642),
        Cinema(11, "Cinepolis La Cañada", City.Malaga, 36.517246, -4.872607),
        Cinema(12, "Odeon Multicines Tres Cantos", City.Madrid, 40.604049, -3.708081),
        Cinema(13, "Cines Filmax Gran Via", City.Barcelona, 41.358917, 2.097245),
        Cinema(14, "Kinepolis Ciudad de la Imagen", City.Madrid, 40.418145, -3.786132),
        Cinema(15, "Multicines Zamora", City.Zamora, 41.503027, -5.754551),
        Cinema(16, "Cines Moderno", City.Sevilla, 37.392540, -5.994810),
        Cinema(17, "Cines Almenara", City.Murcia, 37.649068, -1.698711),
        Cinema(18, "Multicines 7", City.Bilbao, 43.26564477464586, -2.949996146743452),
        Cinema(19, "Yelmo Premium Vallsur", City.Valladolid, 41.62363320813679, -4.750028968941341),
        Cinema(20, "Cines Babel", City.Valencia, 39.465489, -0.374805)
    )

    private fun getRandomCinemaId(): Int =
        loadCinemas().random().id

    private fun movieCinemaManualInserts(db: SQLiteDatabase) {
        val movies = loadMovies()
        movies.forEach { movie ->
            val selectedCinemas = mutableSetOf<Int>()
            while (selectedCinemas.size < 5) {
                val randId = getRandomCinemaId()
                if (!selectedCinemas.contains(randId)) {
                    db.execSQL("INSERT INTO Movie_Cinema(movie_id, cinema_id) values (${movie.id}, $randId);")
                    selectedCinemas.add(randId)
                }
            }
        }
    }
}