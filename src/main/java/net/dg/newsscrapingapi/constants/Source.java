package net.dg.newsscrapingapi.constants;

public enum Source {
  GHIZMODO("Ghizmodo.com"),
  MASHABLE("Mashable.com");

  private String source;

  public String getSource() {
    return this.source;
  }

  private Source(String source) {
    this.source = source;
  }
}
