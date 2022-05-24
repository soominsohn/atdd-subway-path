package wooteco.subway.dto;

import javax.validation.constraints.Min;
import org.jetbrains.annotations.NotNull;
import wooteco.subway.domain.section.Section;


public class SectionRequest {

    @NotNull
    private Long upStationId;

    @NotNull
    private Long downStationId;

    @Min(0)
    private int distance;

    public SectionRequest() {
    }

    public SectionRequest(Long upStationId, Long downStationId, int distance) {
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    public Section toSection(Long lineId) {
        return new Section(lineId, upStationId, downStationId, distance);
    }
}
