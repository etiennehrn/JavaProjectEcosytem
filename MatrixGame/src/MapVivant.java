import java.util.Random;

public class MapVivant {
    private EtreVivant[][] mapVivants;
    private int rows;
    private int cols;

    public MapVivant(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        mapVivants = new EtreVivant[rows][cols];
    }

    // On mets des humains et des zombies sur la carte
    public void populate(int nbHumains, int nbZombies, int nbAnimaux, MapEnvironnement map) {
        Random random = new Random();

        for (int i = 0; i < nbHumains; i++) {
            boolean placed = false;
            int compt = 0;
            while (!placed && compt <= 10) {
                int row = random.nextInt(map.getRows());
                int col = random.nextInt(map.getCols());

                if (!map.getCell(row, col).isObstacle() && mapVivants[row][col] == null) {
                    mapVivants[row][col] = new Humain(row, col);
                    placed = true;
                }
                compt++;
            }
        }
        // Zombie après
        for (int i = 0; i < nbZombies; i++) {
            boolean placed = false;
            int compt = 0;
            while (!placed && compt <= 10) {
                int row = random.nextInt(map.getRows());
                int col = random.nextInt(map.getCols());

                if (!map.getCell(row, col).isObstacle() && mapVivants[row][col] == null) {
                    mapVivants[row][col] = new Zombie(row, col);
                    placed = true;
                }
                compt++;
            }
        }

        // Animaux
        for (int i = 0; i < nbAnimaux; i++) {
            boolean placed = false;
            int compt = 0;
            while (!placed && compt <= 10) {
                int row = random.nextInt(map.getRows());
                int col = random.nextInt(map.getCols());

                if (!map.getCell(row, col).isObstacle() && mapVivants[row][col] == null) {
                    ajouterAnimalAleatoire(row, col);
                    placed = true;
                }
                compt++;
            }
        }
    }

    // On fais la mise à jour des déplacements
    public void update(MapEnvironnement grid) {

        // Copier temporairement la carte pour éviter les conflits
        EtreVivant[][] tempMap = new EtreVivant[grid.getRows()][grid.getCols()];
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                tempMap[row][col] = mapVivants[row][col];
            }
        }


        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                EtreVivant vivant = tempMap[row][col];

                if (vivant == null) {
                    continue;
                }

                vivant.gen_deplacement(this, grid, row, col);
                if (vivant instanceof Zombie zombie) {
                    zombie.transformNearbyHumans(this);
                }
            }
        }
    }


    // Pour ajouter un animal aléatoire

    public void ajouterAnimalAleatoire(int row, int col) {
        Animaux.Type[] types = Animaux.Type.values();
        Random random = new Random();
        Animaux.Type randomType = types[random.nextInt(types.length)];

        // Placer un animal correspondant dans la mapVivants
        switch (randomType) {
            case DEER -> mapVivants[row][col] = new Deer(row, col);
            case BEAR -> mapVivants[row][col] = new Bear(row, col);
            case BOAR -> mapVivants[row][col] = new Boar(row, col);
            case FOX -> mapVivants[row][col] = new Fox(row, col);
            case WOLF -> mapVivants[row][col] = new Wolf(row, col);
            case BUNNY -> mapVivants[row][col] = new Bunny(row, col);
        }
    }
    // Pour savoir si l'etre vivant à row et bound est bien sur la carte
    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
    }
    // Getter et setter
    public EtreVivant getEtreVivant(int row, int col) {
        return mapVivants[row][col];
    }
    public void setEtreVivant(int row, int col, EtreVivant etre) {
        mapVivants[row][col] = etre;
    }
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
}
