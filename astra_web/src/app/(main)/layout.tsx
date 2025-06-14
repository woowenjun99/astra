import Header from "@/component/Header";
import { NavbarSimple } from "@/component/Navbar";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <Header />
      <div style={{ display: "flex", backgroundColor: "#F9FAFB" }}>
        <NavbarSimple />
        {children}
      </div>
    </div>
  );
}
