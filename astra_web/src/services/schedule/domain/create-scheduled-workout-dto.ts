export interface CreateScheduledWorkoutDTO {
  date: Date;
  frequency?: "Daily" | "Weekly" | "Every 2 weeks" | "Monthly";
  isRecurring: boolean;
  remarks: string;
  shouldSendReminder: boolean;
  time: string;
  title: string;
  workoutType: "Strength Training" | "Running";
}
