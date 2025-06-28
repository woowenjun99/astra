export class Formatter {
  /**
   * Given a distance in meters, convert it into x km y m
   * format.
   *
   * @param distance The distance in meters
   * @returns The distance in x km y m format
   */
  public static formatDistance(distance: number): string {
    const metres = distance % 1000;
    const kilometres = Math.floor(distance / 1000);
    if (kilometres !== 0) {
      return `${kilometres}.${metres} km`;
    }
    return `${metres} m`;
  }

  /**
   * Given a time in seconds, format in x h y min z s format
   *
   * @param time Time in seconds
   * @returns Time format in x h y min z s format
   */
  public static formatTimeElapsed(time: number): string {
    const hours = Math.floor(time / 3600);
    const remaining = time - hours * 3600;
    const minutes = Math.floor(remaining / 60);
    const seconds = time % 60;
    let duration = "";
    if (hours) {
      duration += `${hours} h `;
    }
    if (minutes) {
      duration += `${minutes} min `;
    }
    if (seconds) {
      duration += `${seconds} s`;
    }
    return duration;
  }
}
