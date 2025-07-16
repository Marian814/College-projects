library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity IFetch is
Port ( Jump: in std_logic;
       JumpAddress: in std_logic_vector(31 downto 0);
       PCSrc: in std_logic;
       BranchAddress: in std_logic_vector(31 downto 0);
       PCOut: out std_logic_vector(31 downto 0);
       Instruction: out std_logic_vector(31 downto 0);
       Enable: in std_logic;
       Reset: in std_logic;
       clk: in std_logic);
end IFetch;

architecture Behavioral of IFetch is

signal PC_in: std_logic_vector(31 downto 0);
signal PC_out: std_logic_vector(31 downto 0);
signal Sum_out: std_logic_vector(31 downto 0);
signal Mux1: std_logic_vector(31 downto 0);
signal Address: std_logic_vector(4 downto 0);

type tROM is array(0 to 31) of std_logic_vector(31 downto 0);

signal ROM: tROM := (
    B"001000_00000_00001_0000000000000000",     -- X"20010000", 00: ADDI $1, $0, 0
    B"001000_00000_00010_0000000000000001",     -- X"20020001", 01: ADDI $2, $0, 1    
    B"001000_00000_00011_0000000000000000",     -- X"20030000", 02: ADDI $3, $0, 0    
    B"001000_00000_00100_0000000000000100",     -- X"20040004", 03: ADDI $4, $0, 4
    B"101011_00011_00001_0000000000000000",     -- X"AC610000", 04: SW $1, 0($3)
    B"101011_00100_00010_0000000000000000",     -- X"AC820000", 05: SW $2, 0($4)
    B"100011_00011_00001_0000000000000000",     -- X"8C610000", 06: LW $1, 0($3)
    B"100011_00100_00010_0000000000000000",     -- X"8C820000", 07: LW $2, 0($4)
    B"000000_00001_00010_00101_00000_100000",   -- X"00222820", 08: ADD $5, $1, $2
    B"000000_00000_00010_00001_00000_100000",   -- X"00020820", 09: ADD $1, $0, $2
    B"000000_00000_00101_00010_00000_100000",   -- X"00051020", 10: ADD $2, $0, $5
    B"000010_00000000000000000000001000",       -- X"08000008", 11: J 8
    
    others => X"00000000"); 
    
begin

Address <= PC_out(6 downto 2);

process(Address)
begin
case Address is
         when "00000" => Instruction <= ROM(0);
         when "00001" => Instruction <= ROM(1);
         when "00010" => Instruction <= ROM(2);
         when "00011" => Instruction <= ROM(3);
         when "00100" => Instruction <= ROM(4);
         when "00101" => Instruction <= ROM(5);
         when "00110" => Instruction <= ROM(6);
         when "00111" => Instruction <= ROM(7);
         when "01000" => Instruction <= ROM(8);
         when "01001" => Instruction <= ROM(9);
         when "01010" => Instruction <= ROM(10);
         when "01011" => Instruction <= ROM(11);
         when "01100" => Instruction <= ROM(12);
         when "01101" => Instruction <= ROM(13);
         when "01110" => Instruction <= ROM(14);
         when "01111" => Instruction <= ROM(15);
         when "10000" => Instruction <= ROM(16);
         when "10001" => Instruction <= ROM(17);
         when others => Instruction <= x"00000000";
        end case;
end process;

process(clk, Reset)
begin
    if Reset = '1' then
        PC_out <= x"00000000";
    elsif rising_edge(clk) then
        if enable = '1' then
            PC_out <= PC_in;
        end if;
    end if;
end process;

Sum_out <= PC_out + 4;

process(PCSrc)
begin
    case PCSrc is
        when '0' => Mux1 <= Sum_out;
        when '1' => Mux1 <= BranchAddress;
    end case;
end process;

process(Jump)
begin
case Jump is
    when '0' => PC_in <= Mux1;
    when '1' => PC_in <= JumpAddress;
end case;
end process;

PCOut <= Sum_out;

end Behavioral;
