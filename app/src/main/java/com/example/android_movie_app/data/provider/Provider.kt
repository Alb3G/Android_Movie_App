package com.example.android_movie_app.data.provider

import com.example.android_movie_app.R
import com.example.android_movie_app.data.model.Cinema
import com.example.android_movie_app.data.model.City
import com.example.android_movie_app.data.model.Movie
import com.example.android_movie_app.data.model.MovieCinema

class Provider {
    companion object {
        val movieList = mutableListOf(
            Movie(1,"La vida es bella", "Un padre judío-italiano que utiliza su imaginación y humor para proteger a su hijo de los horrores de un campo de concentración nazi, transformando la tragedia en un juego para mantener viva la esperanza.",
                R.drawable.la_vida_es_bella,116, 1997, "Italia", ""
            ),
            Movie(2,"El padrino", "El envejecido patriarca de una dinastía del crimen organizado transfiere el control de su imperio clandestino a su hijo reacio.",
                R.drawable.elpadrino,175, 1972, "Estados Unidos", ""
            ),
            Movie(3,"El caballero oscuro","Cuando la amenaza conocida como El Joker emerge de su pasado misterioso, causa estragos y caos en la gente de Gotham.",
                R.drawable.el_caballero_oscuro,152, 2008, "Estados Unidos", ""
            ),
            Movie(4,"Pulp Fiction","La vida de dos sicarios de la mafia, un boxeador, la esposa de un gánster y dos bandidos se entrelazan en cuatro historias de violencia y redención.",
                R.drawable.pulpfiction,153, 1994, "Estados Unidos", ""
            ),
            Movie(5,"El Señor de los Anillos","Gandalf y Aragorn lideran el mundo de los hombres contra el ejército de Sauron para distraerlo de Frodo y Sam mientras se acercan al Monte del Destino con el Anillo Único.",
                R.drawable.senhoranillos,201, 2003, "Nueva Zelanda", ""
            ),
            Movie(6,"Forrest Gump","Los presidios de Forrest Gump, un hombre con un coeficiente intelectual bajo, tienen lugar durante varios eventos históricos estadounidenses.",
                R.drawable.forrestgump,142, 1994, "Estados Unidos", ""
            ),
            Movie(7, "Origen","Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartición de sueños recibe la tarea inversa de plantar una idea en la mente de un director ejecutivo.",
                R.drawable.origen,148, 2010, "Estados Unidos", ""
            ),
            Movie(8,"El club de la lucha","Un oficinista insomne y un fabricante de jabón desmotivado forman un club de lucha clandestino que se convierte en algo mucho más grande.",
                R.drawable.clublucha,139, 1999, "Estados Unidos", ""
            ),
            Movie(9,"El Imperio Contraataca","Después de que los rebeldes sean brutalmente sobrepasados por el Imperio en el planeta helado Hoth, Luke Skywalker comienza su entrenamiento Jedi con Yoda, mientras sus amigos son perseguidos por Darth Vader.",
                R.drawable.imperiocontraataca,124, 1980, "Estados Unidos", ""
            ),
            Movie(10,"El bueno, el malo y el feo","Un cazarrecompensas se asocia con un hombre para encontrar una fortuna en oro enterrado en un cementerio remoto.",
                R.drawable.buenomalofeo,161, 1966, "Italia", ""
            ),
            Movie(11,"Matrix","Un hacker informático aprende de rebeldes misteriosos sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores.",
                R.drawable.matrix,131, 1999, "Estados Unidos", ""
            ),
            Movie(12,"Uno de los nuestros","La historia de Henry Hill y su vida en la mafia, abarcando su relación con su esposa Karen Hill y sus socios mafiosos Jimmy Conway y Tommy DeVito.",
                R.drawable.uno_de_los_nuestros,148, 1990, "Estados Unidos", ""
            ),
            Movie(13,"La lista de Schindler","En la Polonia ocupada por los alemanes durante la Segunda Guerra Mundial, Oskar Schindler se preocupa gradualmente por su fuerza laboral judía después de presenciar su persecución por parte de los nazis.",
                R.drawable.la_lista_de_schindler,195, 1993, "Estados Unidos", ""
            ),
            Movie(14,"Interestelar","Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento por asegurar la supervivencia de la humanidad.",
                R.drawable.interstellar,169, 2014, "Estados Unidos", ""
            ),
            Movie(15,"Parásitos","La codicia y la discriminación de clase amenazan la recién formada relación simbiótica entre la adinerada familia Park y el empobrecido clan Kim.",
                R.drawable.parasitos,132, 2019, "Corea del sur",""
            ),
            Movie(16,"La milla verde","Las vidas de los guardias en el corredor de la muerte se ven afectadas por uno de sus reclusos: un hombre negro acusado de asesinato infantil y violación, pero que tiene un don misterioso.",
                R.drawable.la_milla_verde,180, 1999, "Estados Unidos", ""
            ),
            Movie(17,"Cadena perpetua","Dos hombres encarcelados establecen una fuerte amistad a lo largo de los años, encontrando consuelo y redención eventual a través de actos de decencia común.",
                R.drawable.cadena_perpetua,142, 1994, "Estados Unidos", ""
            ),
            Movie(18,"El pianista","Narra la historia real de Władysław Szpilman, un pianista judío-polaco que lucha por sobrevivir durante la ocupación nazi en la Segunda Guerra Mundial, utilizando su talento y resiliencia en medio del horror.",
                R.drawable.el_pianista,148, 2002, "Reino Unido", ""
            )
        )
        val cinemaList = mutableListOf(
            Cinema(1, "Cine Vialia", City.Malaga, 36.712041088693745, -4.433696489879524),
            Cinema(2, "Yelmo Plaza Mayor", City.Malaga, 36.6570343624005, -4.479458003374459),
            Cinema(3, "Cines Verdi", City.Madrid, 40.43669344061255, -3.704296878719535),
            Cinema(4, "Capitol", City.Madrid, 40.4206016620782, -3.706560645554721),
            Cinema(5, "Cinemes Verdi", City.Barcelona, 41.40689588224367, 2.1564225855899086),
            Cinema(6, "Cinemes Girona", City.Barcelona, 41.402260665343086, 2.165348976900318),
            Cinema(7, "Codex Cinema", City.Lugo, 43.024218990433816, -7.566038413369055),
            Cinema(8, "Yelmo As termas", City.Lugo, 43.03877476362686, -7.570244116967229),
            Cinema(9, "Cinema NOS", City.Portugal, 38.7718093054458, -9.160708892144276),
            Cinema(10, "Cinema NOS Palacio do Gelo", City.Portugal, 40.71263408423472, -7.88853821830642),
            Cinema(11, "Cinepolis La Cañada", City.Malaga, 36.52193146857712, -4.876636345708138),
            Cinema(12, "Odeon Multicines Tres Cantos", City.Madrid, 40.59985454777887, -3.70862601671167),
            Cinema(13, "Cines Filmax Gran Via", City.Barcelona, 41.35852327758783, 2.1284761679771993),
            Cinema(14, "Kinepolis Ciudad de la Imagen", City.Madrid, 40.39403609451144, -3.79673781672014),
            Cinema(15, "Multicines Zamora", City.Zamora, 41.51094395278755, -5.742651116673532),
            Cinema(16, "Cines Moderno", City.Sevilla, 41.510951986852, -5.742586743659274),
            Cinema(17, "Cines Almenara", City.Murcia, 37.63446879319063, -1.6989067168301681),
            Cinema(18, "Multicines 7", City.Bilbao, 43.26164004600719, -2.945396362626624),
            Cinema(19, "Yelmo Premium Vallsur", City.Valladolid, 41.62370401236042, -4.750093816668762),
            Cinema(20, "Cines Babel", City.Valencia, 39.46988700500927, -0.3572719321006511)
        )

        fun preloadMovieCinemaRelations(): List<MovieCinema> {
            val movieCinemas = mutableListOf<MovieCinema>()

            val movies = movieList.map { it.id }
            val cinemas = cinemaList.map { it.id }

            movies.forEach { movieId ->
                val selectedCinemas = cinemas.shuffled().take(5)
                selectedCinemas.forEach { cinemaId ->
                    movieCinemas.add(MovieCinema(movieId, cinemaId))
                }
            }

            return movieCinemas
        }
    }
}


