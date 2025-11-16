package arkanoid.source.code.gameplay.gameobject.brick;

public class Map {
    private static final BrickSet[] map = new BrickSet[11];

    static {
        map[1] = new StandardBrickSet();
        map[1].readData("/arkanoid/resources/mapdata/map1.txt");
        map[2] = new StandardBrickSet();
        map[2].readData("/arkanoid/resources/mapdata/map2.txt");
        map[3] = new StandardBrickSet();
        map[3].readData("/arkanoid/resources/mapdata/map3.txt");
        map[4] = new StandardBrickSet();
        map[4].readData("/arkanoid/resources/mapdata/map4.txt");
        map[5] = new StandardBrickSet();
        map[5].readData("/arkanoid/resources/mapdata/map5.txt");
        map[6] = new StandardBrickSet();
        map[6].readData("/arkanoid/resources/mapdata/map6.txt");
        map[7] = new StandardBrickSet();
        map[7].readData("/arkanoid/resources/mapdata/map7.txt");
        map[8] = new StandardBrickSet();
        map[8].readData("/arkanoid/resources/mapdata/map8.txt");
        map[9] = new StandardBrickSet();
        map[9].readData("/arkanoid/resources/mapdata/map9.txt");
        map[10] = new StandardBrickSet();
        map[10].readData("/arkanoid/resources/mapdata/map10.txt");
    }

    public static BrickSet getMap(int index) {
        return map[index];
    }
}
