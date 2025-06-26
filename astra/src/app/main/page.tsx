"use client";
import ThemeToggle from "@/components/theme-toggle";
import RecentWorkoutDashboardCard from "@/services/fitness/presentation/recent-workout-dashboard-card";

export default function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-gray-100 dark:from-gray-900 dark:to-gray-800 p-6">
      <ThemeToggle />
      <div className="flex flex-col">
        <div className="mb-5">
          <h1 className="text-3xl font-bold">Welcome Back</h1>
          <p className="text-gray-500 dark:text-gray-400">
            {"Here's your fitness summary for today"}
          </p>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <RecentWorkoutDashboardCard />
        </div>
      </div>
    </div>
  );
}
