package com.github.ibspoof.dse.webapp.models.accessors;


import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.github.ibspoof.dse.webapp.models.ThisDay;

@Accessor
public interface ThisDayAccessor {

    @Query("SELECT * FROM this_day")
//    @QueryParameters(consistency="LOCAL_ONE", fetchSize = 10)
    Result<ThisDay> getAll();

    @Query("SELECT * FROM this_day where date = :date")
    Result<ThisDay> getAllByDate(@Param("date") String date);

    @Query("SELECT * FROM this_day where date = :date and year = :year")
    Result<ThisDay> getByDateAndYear(@Param("date") String date, @Param("year") Integer year);

}