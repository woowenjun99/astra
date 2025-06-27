import type { MetadataRoute } from "next";

export default function mainfest(): MetadataRoute.Manifest {
  return {
    background_color: "#FFFFFF",
    description:
      "A super Progressive Web App that is able to keep track of fitness",
    display: "standalone",
    icons: [
      {
        src: "/android-chrome-192x192.png",
        sizes: "192x192",
        type: "image/png",
      },
      {
        src: "/android-chrome-512x512.png",
        sizes: "512x512",
        type: "image/png",
      },
    ],
    name: "Astra",
    start_url: "/",
    theme_color: "#000000",
  };
}
