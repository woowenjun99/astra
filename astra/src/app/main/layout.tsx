"use client";
import ThemeToggle from "@/components/theme-toggle";
import type { ReactNode } from "react";

export default function Layout({ children }: { children: ReactNode }) {
  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-gray-100 dark:from-gray-900 dark:to-gray-800 p-6">
      <ThemeToggle />
      {children}
    </div>
  );
}
