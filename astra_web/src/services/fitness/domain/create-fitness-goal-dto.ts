export interface CreateFitnessGoalDTO {
  category: string;
  description: string | null;
  targetDate: Date;
  targetValue: number;
  title: string;
}
