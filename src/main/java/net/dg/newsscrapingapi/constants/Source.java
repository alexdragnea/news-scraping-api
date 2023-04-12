package net.dg.newsscrapingapi.constants;

public enum Source {
  GHIZMODO("Ghizmodo.com"),
  MASHABLE("Mashable.com"),
  MEDIAFAX("Mediafax.ro"),
  GALAXYTECH("Galaxy-tech.ro");

  private final String sourceName;

  public String getSourceName() {
    return this.sourceName;
  }

  Source(String sourceName) {
    this.sourceName = sourceName;
  }
}
