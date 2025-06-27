"use client";
import ThemeToggle from "@/components/theme-toggle";
import type { ReactNode } from "react";

export default function Layout({ children }: { children: ReactNode }) {
  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 p-6">
      <ThemeToggle />
      {children}
    </div>
  );
}
