import java.util.Random;

public class MapVivant {
    private EtreVivant[][] mapVivants;

    public MapVivant(int rows, int cols) {
        mapVivants = new EtreVivant[rows][cols];
    }

    // On mets des humains et des zombies sur la carte
    public void populate(int nbHumains, int nbZombies, MapEnvironnement map) {
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
    }

    // On fais la mise à jour des déplacements
    public void update(MapEnvironnement grid) {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                if (mapVivants[row][col] != null) {
                    mapVivants[row][col].gen_deplacement(this, grid, row, col);
                }
            }
        }
    }

    // Getter et setter
    public EtreVivant getEtreVivant(int row, int col) {
        return mapVivants[row][col];
    }
    public void setEtreVivant(int row, int col, EtreVivant etre) {
        mapVivants[row][col] = etre;
    }
}
