package arkanoid.source.code.gameplay.brick;

public class Map {
    private static final BrickSet[] map = new BrickSet[13];

    static {
        map[1] = new BrickSet();
        map[1].readData("/arkanoid/resources/mapdata/map1.txt");
        map[2] = new BrickSet();
        map[2].readData("/arkanoid/resources/mapdata/map2.txt");
        map[3] = new BrickSet();
        map[3].readData("/arkanoid/resources/mapdata/map3.txt");
        map[4] = new BrickSet();
        map[4].readData("/arkanoid/resources/mapdata/map4.txt");
        map[5] = new BrickSet();
        map[5].readData("/arkanoid/resources/mapdata/map5.txt");
        map[6] = new BrickSet();
        map[6].readData("/arkanoid/resources/mapdata/map6.txt");
        map[7] = new BrickSet();
        map[7].readData("/arkanoid/resources/mapdata/map7.txt");
        map[8] = new BrickSet();
        map[8].readData("/arkanoid/resources/mapdata/map8.txt");
        map[9] = new BrickSet();
        map[9].readData("/arkanoid/resources/mapdata/map9.txt");
        map[10] = new BrickSet();
        map[10].readData("/arkanoid/resources/mapdata/map10.txt");
        map[11] = new BrickSet();
        map[11].readData("/arkanoid/resources/mapdata/map11.txt");
        map[12] = new BrickSet();
        map[11].readData("/arkanoid/resources/mapdata/map12.txt");
    }

    public static BrickSet getMap(int index) {
        return map[index];
    }
}
