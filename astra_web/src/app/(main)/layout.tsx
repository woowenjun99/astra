import Header from "@/component/Header";
import { NavbarSimple } from "@/component/Navbar";
import { Box, Group } from "@mantine/core";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <Header />
      <Group bg="#F9FAFB" h="100dvh" align="start" w="100dvw" wrap="nowrap">
        <NavbarSimple />
        <Box style={{ flex: 1, height: "100%" }} p="md">
          {children}
        </Box>
      </Group>
    </div>
  );
}
