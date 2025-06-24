import type { MetadataRoute } from "next";

export default function mainfest(): MetadataRoute.Manifest {
  return {
    name: "Astra",
    start_url: "/",
  };
}
