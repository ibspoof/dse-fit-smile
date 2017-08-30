package com.github.ibspoof.dse.webapp.repositories;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import com.github.ibspoof.dse.webapp.models.ThisDay;
import com.github.ibspoof.dse.webapp.models.accessors.ThisDayAccessor;
import org.springframework.stereotype.Repository;

import static com.datastax.driver.mapping.Mapper.Option.consistencyLevel;
import static com.datastax.driver.mapping.Mapper.Option.ifNotExists;

@Repository
public class OnThisDayRepository extends AbstractRepository {

    private static Mapper<ThisDay> thisDayMapper;
    private static ThisDayAccessor thisDayAccessor;


    OnThisDayRepository() {
        if (thisDayMapper == null) {
            thisDayMapper = mappingManager.mapper(ThisDay.class);
        }

        if (thisDayAccessor == null) {
            thisDayAccessor = mappingManager.createAccessor(ThisDayAccessor.class);
        }
    }

    public boolean save(ThisDay thisDay) {
        try {
            thisDayMapper.save(thisDay, ifNotExists(true), consistencyLevel(ConsistencyLevel.LOCAL_QUORUM));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Result<ThisDay> getAllByDay(String day) {
        return thisDayAccessor.getAllByDate(day);
    }

    public Result<ThisDay> getByDayAndYear(String day, Integer year) {
        return thisDayAccessor.getByDateAndYear(day, year);
    }

}
