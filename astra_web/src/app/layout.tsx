import "@mantine/core/styles.css";
import "@mantine/notifications/styles.css";
import "@mantine/dates/styles.css";
import { MantineProvider } from "@mantine/core";
import { Notifications } from "@mantine/notifications";
import { ModalsProvider } from "@mantine/modals";

import ClientLayout from "@/component/ClientLayout";
import type { Metadata } from "next";

export const metadata: Metadata = {
  description: "A super app designed to improve lives",
  title: "Astra",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <MantineProvider>
          <ClientLayout>
            <ModalsProvider>{children}</ModalsProvider>
            <Notifications />
          </ClientLayout>
        </MantineProvider>
      </body>
    </html>
  );
}
