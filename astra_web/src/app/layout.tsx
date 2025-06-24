import "@mantine/core/styles.css";
import "@mantine/notifications/styles.css";
import "@mantine/dates/styles.css";
import "@mantine/charts/styles.css";
import { MantineProvider } from "@mantine/core";
import { Notifications } from "@mantine/notifications";

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
            {children}
            <Notifications />
          </ClientLayout>
        </MantineProvider>
      </body>
    </html>
  );
}
