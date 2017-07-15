package datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by georg on 30/06/2017.
 */
public class Datasource {
    public static final String DB_NAME = "rosterdata.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\georg\\Desktop\\JavaCode\\Roster8\\" + DB_NAME;

    public static final String TABLE_WEEKS = "weeks";
    public static final String COLUMN_WEEK_ID = "_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_ROSTERED_HOURS = "rostered_hours";
    public static final String COLUMN_SATURDAY_HOURS = "saturday_hours";
    public static final String COLUMN_SUNDAY_HOURS = "sunday_hours";
    public static final String COLUMN_WEEK_ROSTER = "roster_id";

    public static final String TABLE_ROSTERS = "rosters";
    public static final String COLUMN_ROSTER_ID = "_id";
    public static final String COLUMN_ROSTER_NAME = "name";

    public static final String TABLE_TEAMS = "teams";
    public static final String COLUMN_TEAM_ID = "_id";
    public static final String COLUMN_TEAM_NAME = "name";
    public static final String COLUMN_START_WEEK = "start_week";
    public static final String COLUMN_TEAM_ROSTER = "roster";

    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_EMPLOYEE_ID = "_id";
    public static final String COLUMN_EMPLOYEE_NAME = "name";
    public static final String COLUMN_EMPLOYEE_TEAM = "team";
    public static final String COLUMN_IS_FULL_TIME = "is_full_time";
    public static final String COLUMN_E_TOTAL_HOURS = "total_hours";
    public static final String COLUMN_E_SATURDAY_HOURS = "saturday_hours";
    public static final String COLUMN_E_SUNDAY_HOURS = "sunday_hours";
    public static final String COLUMN_E_PH_HOURS = "ph_hours";
    public static final String COLUMN_E_PH_CREDIT = "ph_credit";
    public static final String COLUMN_E_SHIFT_CHANGES = "shift_changes";


    public static final String CREATE_TABLE_WEEKS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_WEEKS + " ( " + COLUMN_WEEK_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_NUMBER + " INTEGER NOT NULL, " + COLUMN_ROSTERED_HOURS + " INTEGER NOT NULL, "
                    + COLUMN_SATURDAY_HOURS + " INTEGER NOT NULL, " + COLUMN_SUNDAY_HOURS + " INTEGER NOT NULL, "
                    + COLUMN_WEEK_ROSTER + " INTEGER NOT NULL, FOREIGN KEY (" + COLUMN_WEEK_ROSTER + ") REFERENCES "
                    + TABLE_ROSTERS + " (" + COLUMN_ROSTER_ID + ") ON DELETE CASCADE)";

    public static final String CREATE_TABLE_ROSTERS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ROSTERS + " ( " + COLUMN_ROSTER_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_ROSTER_NAME + " VARCHAR NOT NULL )";

    public static final String CREATE_TABLE_TEAMS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TEAMS + " ( " + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_TEAM_NAME + " VARCHAR NOT NULL, " + COLUMN_START_WEEK + " INTEGER NOT NULL, "
                    + COLUMN_TEAM_ROSTER + " INTEGER NOT NULL, FOREIGN KEY (" + COLUMN_TEAM_ROSTER + ") REFERENCES "
                    + TABLE_ROSTERS + " (" + COLUMN_ROSTER_ID + ") ON DELETE CASCADE)";

    public static final String CREATE_TABLE_EMPLOYEES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEES + " ( " + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_EMPLOYEE_NAME + " VARCHAR NOT NULL, " + COLUMN_EMPLOYEE_TEAM + " INTEGER NOT NULL, "
                    + COLUMN_IS_FULL_TIME + " BOOLEAN NOT NULL, " + COLUMN_E_TOTAL_HOURS + " INTEGER NOT NULL, "
                    + COLUMN_E_SATURDAY_HOURS + " INTEGER NOT NULL, " + COLUMN_E_SUNDAY_HOURS + " INTEGER NOT NULL, "
                    + COLUMN_E_PH_HOURS + " INTEGER NOT NULL, " + COLUMN_E_PH_CREDIT + " INTEGER NOT NULL, "
                    + COLUMN_E_SHIFT_CHANGES + " INTEGER, FOREIGN KEY (" + COLUMN_EMPLOYEE_TEAM + ") REFERENCES "
                    + TABLE_TEAMS + " (" + COLUMN_TEAM_ID + ") ON DELETE CASCADE)";

    public static final String INSERT_ROSTER = "INSERT INTO " + TABLE_ROSTERS +
            "(" + COLUMN_ROSTER_NAME + ") VALUES(?)";

    public static final String INSERT_WEEK = "INSERT INTO " + TABLE_WEEKS +
            "(" + COLUMN_NUMBER + ", " + COLUMN_ROSTERED_HOURS + ", " + COLUMN_SATURDAY_HOURS +
            ", " + COLUMN_SUNDAY_HOURS + ", " + COLUMN_WEEK_ROSTER + ") VALUES(?, ?, ?, ?, ?)";

    public static final String INSERT_TEAM = "INSERT INTO " + TABLE_TEAMS + " (" + COLUMN_TEAM_NAME + ", " +
            COLUMN_START_WEEK + ", " + COLUMN_TEAM_ROSTER + ") VALUES(?, ?, ?)";

    public static final String INSERT_EMPLOYEE = "INSERT INTO " + TABLE_EMPLOYEES + " (" + COLUMN_EMPLOYEE_NAME +
            ", " + COLUMN_EMPLOYEE_TEAM + ", " + COLUMN_IS_FULL_TIME + ", " + COLUMN_E_TOTAL_HOURS +
            ", " + COLUMN_E_SATURDAY_HOURS + ", " + COLUMN_E_SUNDAY_HOURS + ", " + COLUMN_E_PH_HOURS +
            ", " + COLUMN_E_PH_CREDIT + ", " + COLUMN_E_SHIFT_CHANGES + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String READ_ROSTERS = "SELECT * FROM " + TABLE_ROSTERS;

    public static final String READ_WEEKS = "SELECT * FROM " + TABLE_WEEKS + " WHERE " +
            COLUMN_WEEK_ROSTER + " = ?";

    public static final String READ_TEAMS_FROM_ROSTER_ID = "SELECT * FROM " + TABLE_TEAMS +
            " WHERE " + COLUMN_TEAM_ROSTER + " = ?";

    public static final String READ_EMPLOYEES_FROM_TEAM_ID = "SELECT * FROM " + TABLE_EMPLOYEES +
            " WHERE " + COLUMN_EMPLOYEE_TEAM + " = ?";

    public static final String DELETE_ROSTER = "DELETE FROM " + TABLE_ROSTERS + " WHERE " +
            COLUMN_ROSTER_ID + " = ?";

    public static final String DELETE_TEAM = "DELETE FROM " + TABLE_TEAMS + " WHERE " +
            COLUMN_TEAM_ID + " = ?";

    public static final String DELETE_EMPLOYEE = "DELETE FROM " + TABLE_EMPLOYEES + " WHERE " +
            COLUMN_EMPLOYEE_ID + " = ?";

    public static final String UPDATE_TEAM = "UPDATE " + TABLE_TEAMS + " SET " + COLUMN_TEAM_NAME + " = ?, " +
            COLUMN_START_WEEK + " = ?, " + COLUMN_TEAM_ROSTER + " = ? WHERE " + COLUMN_TEAM_ID + " = ?";

    public static final String UPDATE_EMPLOYEE = "UPDATE " + TABLE_EMPLOYEES + " SET " +
            COLUMN_EMPLOYEE_NAME + " = ?, " + COLUMN_IS_FULL_TIME + " = ?, " +
            COLUMN_E_TOTAL_HOURS + " = ?, " + COLUMN_E_SATURDAY_HOURS + " = ?, " + COLUMN_E_SUNDAY_HOURS + " = ?, " +
            COLUMN_E_PH_HOURS + " = ?, " + COLUMN_E_PH_CREDIT + " = ?, " + COLUMN_E_SHIFT_CHANGES +
            " = ? WHERE " + COLUMN_EMPLOYEE_ID + " = ?";

    public static final String UPDATE_ROSTER = "UPDATE " + TABLE_ROSTERS + " SET " + COLUMN_ROSTER_NAME +
            " = ? WHERE " + COLUMN_ROSTER_ID + " = ?";

    public static final String DELETE_ROSTER_WEEKS = "DELETE FROM " + TABLE_WEEKS + " WHERE " + COLUMN_WEEK_ROSTER +
            " = ?";


    private static Datasource instance = new Datasource();

    private Connection conn;

    private Statement pragmaST;
    private PreparedStatement insertRosterST;
    private PreparedStatement insertWeekST;
    private PreparedStatement insertTeamST;
    private PreparedStatement insertEmployeeST;
    private PreparedStatement readRostersST;
    private PreparedStatement readWeeksST;
    private PreparedStatement readTeamsST;
    private PreparedStatement readEmployeesST;
    private PreparedStatement deleteRosterST;
    private PreparedStatement deleteTeamST;
    private PreparedStatement deleteEmployeeST;
    private PreparedStatement updateTeamST;
    private PreparedStatement updateEmployeeST;
    private PreparedStatement updateRosterST;
    private PreparedStatement deleteRosterWeeksST;

    private ObservableList<Roster> rosterData = FXCollections.observableArrayList();
    private ObservableList<Team> teamData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    public Datasource() {
    }

    public static Datasource getInstance() {
        return instance;
    }

    public ObservableList<Roster> getRosterData() {
        return rosterData;
    }

    public ObservableList<Team> getTeamData() {
        return teamData;
    }

    public ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            pragmaST = conn.createStatement();
            pragmaST.execute("PRAGMA foreign_keys = ON");
            this.createTables();
            readWeeksST = conn.prepareStatement(READ_WEEKS);
            readRostersST = conn.prepareStatement(READ_ROSTERS);
            readTeamsST = conn.prepareStatement(READ_TEAMS_FROM_ROSTER_ID);
            readEmployeesST = conn.prepareStatement(READ_EMPLOYEES_FROM_TEAM_ID);
            this.readRosters();

            insertRosterST = conn.prepareStatement(INSERT_ROSTER, Statement.RETURN_GENERATED_KEYS);
            insertWeekST = conn.prepareStatement(INSERT_WEEK);
            insertTeamST = conn.prepareStatement(INSERT_TEAM, Statement.RETURN_GENERATED_KEYS);
            insertEmployeeST = conn.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
            deleteRosterST = conn.prepareStatement(DELETE_ROSTER);
            deleteTeamST = conn.prepareStatement(DELETE_TEAM);
            deleteEmployeeST = conn.prepareStatement(DELETE_EMPLOYEE);
            updateTeamST = conn.prepareStatement(UPDATE_TEAM);
            updateEmployeeST = conn.prepareStatement(UPDATE_EMPLOYEE);
            updateRosterST = conn.prepareStatement(UPDATE_ROSTER);
            deleteRosterWeeksST = conn.prepareStatement(DELETE_ROSTER_WEEKS);

            return true;
        } catch (SQLException e) {
            System.out.println("Failed to open DB: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {

            if (pragmaST != null) {
                pragmaST.close();
            }

            if (insertRosterST != null) {
                insertRosterST.close();
            }

            if (insertWeekST != null) {
                insertWeekST.close();
            }

            if (insertTeamST != null) {
                insertTeamST.close();
            }

            if (insertEmployeeST != null) {
                insertEmployeeST.close();
            }

            if (readWeeksST != null) {
                readWeeksST.close();
            }

            if (readRostersST != null) {
                readRostersST.close();
            }

            if (readTeamsST != null) {
                readTeamsST.close();
            }

            if (readEmployeesST != null) {
                readEmployeesST.close();
            }

            if (deleteRosterST != null) {
                deleteRosterST.close();
            }

            if (deleteTeamST != null) {
                deleteTeamST.close();
            }

            if (updateTeamST != null) {
                updateTeamST.close();
            }

            if (updateEmployeeST != null) {
                updateEmployeeST.close();
            }

            if (updateRosterST != null) {
                updateRosterST.close();
            }

            if (deleteRosterWeeksST != null) {
                deleteRosterWeeksST.close();
            }

            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println("Could not close the database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private boolean createTables() {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_TABLE_WEEKS);
            statement.execute(CREATE_TABLE_ROSTERS);
            statement.execute(CREATE_TABLE_TEAMS);
            statement.execute(CREATE_TABLE_EMPLOYEES);
            return true;
        } catch (SQLException e) {
            System.out.println("Tables could not be created: " + e.getMessage());
            return false;
        }
    }

    private int insertRoster(String rosterName) {
        try {
            insertRosterST.setString(1, rosterName);
            int affectedRows = insertRosterST.executeUpdate();
            if (affectedRows != 1) {
                System.out.println("Insert Roster failed: Not 1 row inserted");
            }

            ResultSet generatedKeys = insertRosterST.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for roster");
            }
        } catch (SQLException e) {
            System.out.println("Could not insert Roster: " + e.getMessage());
        }
        return -1;
    }

    private void insertWeek(Week week, int roster_id) {
        try {
            insertWeekST.setInt(1, week.getNumber());
            insertWeekST.setInt(2, week.getRosteredHours());
            insertWeekST.setInt(3, week.getSaturdayHours());
            insertWeekST.setInt(4, week.getSundayHours());
            insertWeekST.setInt(5, roster_id);
            int affectedRows = insertWeekST.executeUpdate();
            if (affectedRows != 1) {
                System.out.println("Insert week failed: Not 1 row inserted");
            }
        } catch (SQLException e) {
            System.out.println("Could not insert week: " + e.getMessage());
        }
    }

    public boolean insertRosterFull(Roster roster) {
        try {
            conn.setAutoCommit(false);

            int rosterId = insertRoster(roster.getName());
            for (Week week : roster.getWeeks()) {
                insertWeek(week, rosterId);
            }
            conn.commit();
            roster.setId(rosterId);
            rosterData.add(roster);
            return true;
        } catch (Exception e) {
            System.out.println("Could not insert roster full: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("Could not perform rollback: " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting autocommit");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Could not reset auto-commit! " + e.getMessage());
            }

        }
        return false;
    }

    public int insertTeam(Team team) {
        try {
            int rosterId = team.getRoster().getId();
            if (rosterId < 0) {
                System.out.println("rosterId < 0");
                return -1;
            }
            insertTeamST.setString(1, team.getTeamName());
            insertTeamST.setInt(2, team.getStartWeek());
            insertTeamST.setInt(3, rosterId);
            int affectedRows = insertTeamST.executeUpdate();
            if (affectedRows != 1) {
                System.out.println("Affected rows !- 1");
                return -1;
            }
            ResultSet generatedKeys = insertTeamST.getGeneratedKeys();
            if (generatedKeys.next()) {
                int teamId = generatedKeys.getInt(1);
                team.setId(teamId);
                teamData.add(team);
                return teamId;
            }

        } catch (SQLException e) {
            System.out.println("Could not insert team: " + e.getMessage());
        }
        return -1;
    }


    public int insertEmployee(Employee employee) {
        System.out.println("Team id: " + employee.getTeam().getId());
        int teamId = employee.getTeam().getId();
        if (teamId < 0) {
            System.out.println("teamId < 0");
            return -1;
        }
        try {
            insertEmployeeST.setString(1, employee.getName());
            insertEmployeeST.setInt(2, teamId);
            insertEmployeeST.setBoolean(3, employee.isIsFullTime());
            insertEmployeeST.setInt(4, employee.getTotalHours());
            insertEmployeeST.setInt(5, employee.getSaturdayHours());
            insertEmployeeST.setInt(6, employee.getSundayHours());
            insertEmployeeST.setInt(7, employee.getPhHours());
            insertEmployeeST.setInt(8, employee.getPhCredit());
            insertEmployeeST.setInt(9, employee.getShiftChanges());
            int affectedRows = insertEmployeeST.executeUpdate();
            if (affectedRows != 1) {
                System.out.println("Affected rows != 1");
                return -1;
            }
            ResultSet generatedKeys = insertEmployeeST.getGeneratedKeys();
            if (generatedKeys.next()) {
                int employeeId = generatedKeys.getInt(1);
                employee.setId(employeeId);
                employeeData.add(employee);
                return employeeId;
            }
        } catch (SQLException e) {
            System.out.println("Insert employee failed: " + e.getMessage());
        }
        return -1;


    }

    private ObservableList<Week> readWeeks(int rosterId) throws SQLException {
        readWeeksST.setInt(1, rosterId);
        ResultSet results = readWeeksST.executeQuery();
        ObservableList<Week> weekList = FXCollections.observableArrayList();
        while (results.next()) {
            int number = results.getInt(COLUMN_NUMBER);
            int rosteredHours = results.getInt(COLUMN_ROSTERED_HOURS);
            int saturdayHours = results.getInt(COLUMN_SATURDAY_HOURS);
            int sundayHours = results.getInt(COLUMN_SUNDAY_HOURS);
            weekList.add(new Week(number, rosteredHours, saturdayHours, sundayHours));
        }
        return weekList;
    }

    private void readRosters() {
        try {
            conn.setAutoCommit(false);
            ResultSet results = readRostersST.executeQuery();
            while (results.next()) {
                int rosterId = results.getInt(COLUMN_ROSTER_ID);
                String name = results.getString(COLUMN_ROSTER_NAME);
                Roster roster = new Roster(name, readWeeks(rosterId));
                roster.setId(rosterId);
                rosterData.add(roster);
                readTeams(rosterId, roster);
            }
            conn.commit();
            System.out.println("Database read succesfully");
        } catch (Exception e) {
            try {
                conn.rollback();
                System.out.println("Connection rollback...");
            } catch (SQLException e1) {
                System.out.println("Could not perform rollback: " + e1.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e2) {
                System.out.println("Could not rest autocommit: " + e2.getMessage());
            }
        }
    }

    private void readTeams(int rosterId, Roster roster) throws SQLException {
        readTeamsST.setInt(1, rosterId);
        ResultSet results = readTeamsST.executeQuery();
        while (results.next()) {
            int teamId = results.getInt(COLUMN_TEAM_ID);
            String name = results.getString(COLUMN_TEAM_NAME);
            int startWeek = results.getInt(COLUMN_START_WEEK);
            Team team = new Team(name, startWeek, roster);
            team.setId(teamId);
            teamData.add(team);
            readEmployees(teamId, team);
        }
    }

    private void readEmployees(int teamId, Team team) throws SQLException {
        readEmployeesST.setInt(1, teamId);
        ResultSet results = readEmployeesST.executeQuery();
        while (results.next()) {
            int employeeId = results.getInt(COLUMN_EMPLOYEE_ID);
            String name = results.getString(COLUMN_EMPLOYEE_NAME);
            Boolean is_full_time = results.getBoolean(COLUMN_IS_FULL_TIME);
            int totalHours = results.getInt(COLUMN_E_TOTAL_HOURS);
            int saturdayHours = results.getInt(COLUMN_E_SATURDAY_HOURS);
            int sundayHours = results.getInt(COLUMN_E_SUNDAY_HOURS);
            int phHours = results.getInt(COLUMN_E_PH_HOURS);
            int phCredit = results.getInt(COLUMN_E_PH_CREDIT);
            int shiftChanges = results.getInt(COLUMN_E_SHIFT_CHANGES);

            Employee employee = new Employee(name, team, is_full_time, totalHours,
                    saturdayHours, sundayHours, phHours, phCredit, shiftChanges);

            employee.setId(employeeId);
            employeeData.add(employee);
        }
    }

    public boolean deleteRoster(Roster roster) {
        try {
            deleteRosterST.setInt(1, roster.getId());
            int affectedRows = deleteRosterST.executeUpdate();
            if (affectedRows > 0) {
                rosterData.remove(roster);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Could not delete roster: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteTeam(Team team) {
        try {
            deleteTeamST.setInt(1, team.getId());
            int affectedRows = deleteTeamST.executeUpdate();
            if (affectedRows > 0) {
                teamData.remove(team);
            } else {
                System.out.println("deleteTeam rows < 1");
            }
        } catch (SQLException e) {
            System.out.println("Could not delete team: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteEmployee(Employee employee) {
        try {
            deleteEmployeeST.setInt(1, employee.getId());
            deleteEmployeeST.execute();
            employeeData.remove(employee);
            return true;
        } catch (SQLException e) {
            System.out.println("Could not delete employee: " + e.getMessage());
        }
        return false;
    }

    public boolean updateTeam(Team currentTeam, String newTeamName, int newStartWeek, Roster newRoster) {
        try {
            updateTeamST.setString(1, newTeamName);
            updateTeamST.setInt(2, newStartWeek);
            updateTeamST.setInt(3, newRoster.getId());
            updateTeamST.setInt(4, currentTeam.getId());
            int affectedRows = updateTeamST.executeUpdate();
            currentTeam.setTeamName(newTeamName);
            currentTeam.setStartWeek(newStartWeek);
            currentTeam.setRoster(newRoster);
            return true;
        } catch (SQLException e) {
            System.out.println("Could not update team: " + e.getMessage());
        }
        return false;
    }

    public boolean updateEmployee(Employee employee, String newTeamName, Boolean isFullTime, int totalHours,
                                  int saturdayHours, int sundayHours, int phHours, int phCredit, int shiftChanges) {
        try {
            updateEmployeeST.setString(1, newTeamName);
            updateEmployeeST.setBoolean(2, isFullTime);
            updateEmployeeST.setInt(3, totalHours);
            updateEmployeeST.setInt(4, saturdayHours);
            updateEmployeeST.setInt(5, sundayHours);
            updateEmployeeST.setInt(6, phHours);
            updateEmployeeST.setInt(7, phCredit);
            updateEmployeeST.setInt(8, shiftChanges);
            updateEmployeeST.setInt(9, employee.getId());
            updateEmployeeST.execute();


            employee.setName(newTeamName);
            employee.setIsFullTime(isFullTime);
            employee.setTotalHours(totalHours);
            employee.setSaturdayHours(saturdayHours);
            employee.setSundayHours(sundayHours);
            employee.setPhHours(phHours);
            employee.setPhCredit(phCredit);
            employee.setShiftChanges(shiftChanges);
            return true;
        } catch (SQLException e) {
            System.out.println("Could not update employee: " + e.getMessage());
        }
        return false;
    }

    public boolean updateRoster(Roster roster, String newRosterName, ObservableList<Week> newWeekList) {
        try {
            conn.setAutoCommit(false);
            updateRosterST.setString(1, newRosterName);
            updateRosterST.setInt(2, roster.getId());
            int affectedRosters = updateRosterST.executeUpdate();
            deleteRosterWeeksST.setInt(1, roster.getId());
            int affectedWeeks = deleteRosterWeeksST.executeUpdate();
            for (Week week : newWeekList) {
                insertWeek(week, roster.getId());
            }
            roster.getWeeks().clear();
            roster.getWeeks().addAll(newWeekList);
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Could not rollback: " + e.getMessage());
            }
            System.out.println("Could not update roster: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e2) {
                System.out.println("Could not reset autocommit: " + e2.getMessage());
            }
        }
        return false;
    }


}
