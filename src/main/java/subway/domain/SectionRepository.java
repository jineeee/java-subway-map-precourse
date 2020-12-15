package subway.domain;

import subway.Constant;

import java.util.*;

public class SectionRepository {
    private static LineRepository lineRepository = new LineRepository();
    private static StationRepository stationRepository = new StationRepository();

    public static void addSection(String lineName, String stationName, int order) {
        if (!validate(lineName, stationName)) { // 존재하지 않는 노선 또는 역
            throw new IllegalArgumentException();
        }
        try{
            Line targetLine = lineRepository.lines.stream().filter(l -> lineName.equals(l.getName())).findFirst().get();
            targetLine.stations.add(order, new Station(stationName));
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException();
        }
        System.out.println(String.join(" ", Constant.INFO_PREFIX, Constant.ADD_LINE_SUCCESS));
    }

    public static boolean deleteSection(String lineName, String stationName) {
        if (!validate(lineName, stationName)) { // 존재하지 않는 노선 또는 역
            throw new IllegalArgumentException();
        }
        List<Station> stations = lineRepository.lines.stream().filter(l -> lineName.equals(l.getName())).findFirst().get().stations;
        return stations.removeIf(s -> Objects.equals(s.getName(), stationName));
    }

    static boolean validate(String lineName, String stationName){
        if (!lineRepository.checkExistLine(lineName) || !stationRepository.checkExistStation(stationName)) { // 존재하지 않는 노선
            return false;
        }
        return true;
    }

    public static void printMap(){
        for(int i=0; i<lineRepository.lines.size(); i++){
            System.out.print(String.join(" ", Constant.INFO_PREFIX, lineRepository.lines.get(i).getName()));
            for(int j=0; j<lineRepository.lines.get(i).stations.size(); j++){
                System.out.print(lineRepository.lines.get(i).stations.get(j).getName() + " " );
            }
        }
    }

}
