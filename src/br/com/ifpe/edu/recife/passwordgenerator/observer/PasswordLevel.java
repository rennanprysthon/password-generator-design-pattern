package br.com.ifpe.edu.recife.passwordgenerator.observer;

public enum PasswordLevel {
    EASY(1, "Fácil"), MEDIUM(2, "Média"), HARD(3, "Difícil");

    private final int level;
    private final String label;

    PasswordLevel(int level, String label) {
        this.level = level;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getLevel() {
        return level;
    }

    public boolean isGreatherThan(PasswordLevel passwordLevel) {
        return this.level >= passwordLevel.level;
    }
}
