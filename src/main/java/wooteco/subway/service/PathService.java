package wooteco.subway.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import wooteco.subway.dao.SectionDao;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.fare.Fare;
import wooteco.subway.domain.path.DijkstraStrategy;
import wooteco.subway.domain.path.Path;
import wooteco.subway.domain.station.Station;
import wooteco.subway.dto.PathResponse;
import wooteco.subway.dto.StationResponse;

@Service
public class PathService {
    private final StationDao stationDao;
    private final SectionDao sectionDao;

    public PathService(StationDao stationDao, SectionDao sectionDao) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
    }

    public PathResponse searchPath(Long source, Long target, int age) {
        Path path = new Path(stationDao.findAll(), sectionDao.findAll(), new DijkstraStrategy());

        List<Long> shortestPath = path.getShortestPath(source, target);
        int distance = path.calculateShortestDistance(source, target);

        Fare fare = new Fare(distance, age);

        return new PathResponse(createStationResponseOf(shortestPath), distance, fare.calculateFare());
    }

    private List<StationResponse> createStationResponseOf(List<Long> path) {
        List<Station> stations = stationDao.findByIdIn(path);

        Map<Long, String> stationMap = stations.stream()
                .collect(Collectors.toMap(Station::getId, Station::getName));

        return path.stream()
                .map(node -> new StationResponse(node, stationMap.get(node)))
                .collect(Collectors.toList());
    }
}
