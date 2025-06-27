package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.vo.GetWorkoutVO;
import com.wenjun.astra_app.model.vo.GetWorkoutsVO;
import com.wenjun.astra_persistence.models.manual.WorkoutMetadata;

public interface FitnessService {
    /**
     * Get the top 3 most recent workouts by the user.
     *
     * @return An array of workouts
     * @throws AstraException If the user is not logged in
     */
    GetWorkoutsVO getWorkouts(Long pageSize, Long pageNo, String workoutType, String intensity) throws AstraException;

    /**
     * Create a new workout record
     *
     * @param request The payload passed from the frontend
     * @throws AstraException If the user is not logged in
     */
    void createWorkout(CreateWorkoutDTO request) throws AstraException;

    /**
     * Get the metadata about the workouts such as the total
     * number of workouts, the total duration of all the workouts
     * and the average duration of each workout.
     *
     * @throws AstraException If the user is not logged in
     */
    WorkoutMetadata getWorkoutMetadata() throws AstraException;

    /**
     * Delete a workout based on the workout id
     *
     * @throws AstraException If the user is not logged in.
    */
    void deleteWorkout(Long workoutId) throws AstraException;

    /**
     * Given the workout id, modify the workout
     *
     * @param request The information about the workout
     * @throws AstraException If the user is not logged in.
     * @throws AstraException If the ID is null
     * @throws AstraException If the ID does not belong to a workout that the user creates
     */
    void editWorkout(CreateWorkoutDTO request) throws AstraException;

    /**
     * Given a workout id, get all the runs and exercises associated with it.
     *
     * @return The workout log and all the runs and exercises associated with it
     * @throws AstraException If the user is not logged in.
     */
    GetWorkoutVO getWorkout(Long id) throws AstraException;
}
