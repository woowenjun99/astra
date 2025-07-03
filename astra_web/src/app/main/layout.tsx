"use client";
import type { ReactNode } from "react";
import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import { AppSidebar } from "@/components/app-sidebar";
import { Toaster } from "@/components/ui/sonner";

export default function Layout({ children }: { children: ReactNode }) {
  return (
    <SidebarProvider>
      <AppSidebar />
      <div className="min-h-screen w-full bg-gray-50 dark:bg-gray-900 p-6">
        <SidebarTrigger />
        {children}
      </div>
      <Toaster richColors />
    </SidebarProvider>
  );
}
