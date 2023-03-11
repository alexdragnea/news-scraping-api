package net.dg.newsscrapingapi.constants;

public enum Source {

  GHIZMODO("Ghizmodo"), MASHABLE("MASHABLE");

  private String source;

  public String getSource() {
    return this.source;
  }

  private Source(String source) {
    this.source = source;
  }
}
