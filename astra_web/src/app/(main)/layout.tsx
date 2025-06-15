import Header from "@/component/Header";
import { NavbarSimple } from "@/component/Navbar";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <Header />
      <div style={{ display: "flex", backgroundColor: "#F9FAFB" }}>
        <NavbarSimple />
        <div
          style={{
            width: "75%",
            marginLeft: "auto",
            marginRight: "auto",
            paddingTop: 20,
          }}
        >
          {children}
        </div>
      </div>
    </div>
  );
}
