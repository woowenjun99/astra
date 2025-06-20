export interface FitnessGoal {
  category: string;
  currentValue: number;
  description: string | null;
  targetDate: Date;
  targetValue: number;
  title: string;
  unit: string;
}
