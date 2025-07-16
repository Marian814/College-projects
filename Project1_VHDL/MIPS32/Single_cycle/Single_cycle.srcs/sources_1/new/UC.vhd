library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity UC is
Port ( Instr: in std_logic_vector(5 downto 0);
       RegDst: out std_logic;
       ExtOp: out std_logic;
       ALUSrc: out std_logic;
       Branch: out std_logic;
       Jump: out std_logic;
       ALUOp: out std_logic_vector(1 downto 0);
       MemWrite: out std_logic;
       MemtoReg: out std_logic;
       RegWrt: out std_logic);
end UC;

architecture Behavioral of UC is

begin

process(instr)
begin
    RegDst    <= '0';
    ExtOp     <= '0';
    ALUSrc    <= '0';
    Branch    <= '0';
    Jump      <= '0';
    ALUOp     <= "00";
    MemWrite  <= '0';
    MemtoReg  <= '0';
    RegWrt    <= '0';
    
    case Instr is
        when "000000" =>
            RegDst <= '1';
            RegWrt <= '1';
            ALUOp <= "10";
        when "001000" => -- addi
            ExtOp <= '1';
            ALUSrc <= '1';
            RegWrt <= '1';
            ALUOp <= "00";
        when "100011" => -- lw
            RegWrt <= '1';
            ALUSrc <= '1';
            ExtOp <= '1';
            MemtoReg <= '1';
            ALUOp <= "00";
        when "101011" => -- sw
            ALUSrc <= '1';
            ExtOp <= '1';
            MemWrite <= '1';
            ALUOp <= "00";
        when "000100" => -- beq
            ExtOp <= '1';
            ALUOp <= "01";
            Branch <= '1';
        when "000010" => -- j
            jump <= '1';
        when others => 
            RegDst    <= 'X';
            ExtOp     <= 'X';
            ALUSrc    <= 'X';
            Branch    <= 'X';
            Jump      <= 'X';
            ALUOp     <= "XX";
            MemWrite  <= 'X';
            MemtoReg  <= 'X';
            RegWrt    <= 'X';
    end case;
end process;

end Behavioral;
