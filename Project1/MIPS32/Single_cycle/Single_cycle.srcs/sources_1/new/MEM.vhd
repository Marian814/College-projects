library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity MEM is
Port (MemWrite: in std_logic;
      ALUResIn: in std_logic_vector(31 downto 0);
      RD2: in std_logic_vector(31 downto 0);
      clk: in std_logic;
      en: in std_logic;
      MemData: out std_logic_vector(31 downto 0);
      ALUResOut: out std_logic_vector(31 downto 0));
end MEM;

architecture Behavioral of MEM is

type tRAM is array(0 to 63) of std_logic_vector(31 downto 0);

signal RAM: tRAM := (X"0000000A",
                     X"0000000B",
                     X"0000000C",
                     X"0000000D",
                     X"0000000E",
                     X"0000000F",
                     X"00000009",
                     X"00000008", 
                     others => X"00000000");
signal address: std_logic_vector(5 downto 0);

begin

address <= ALUResIn(7 downto 2); 

process(clk)
begin
    if rising_edge(clk) then
        if en = '1' and MemWrite = '1' then
            RAM(conv_integer(address)) <= RD2;
        end if;
    end if;
end process;

MemData <= RAM(conv_integer(address));

ALUResOut <= ALUResIn;

end Behavioral;
