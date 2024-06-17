package com.example.camelProject;

import com.example.camelProject.model.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectResultRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
       // logger.info("========Mapping row to Project object", rowNum);
        Project s = new Project();
        s.setProjectid(rs.getInt("projectid"));
        s.setProjectcode(rs.getString("projectcode"));
        s.setProjectname(rs.getString("projectname"));
        return s;
    }
}
